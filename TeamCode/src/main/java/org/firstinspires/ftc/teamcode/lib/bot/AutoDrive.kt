package org.firstinspires.ftc.teamcode.lib.bot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.SYSTEM_PERIOD
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.blocks.Block
import org.futurerobotics.jargon.blocks.BlockArrangementBuilder
import org.futurerobotics.jargon.blocks.buildBlockSystem
import org.futurerobotics.jargon.blocks.control.*
import org.futurerobotics.jargon.blocks.functional.ExternalQueue
import org.futurerobotics.jargon.linalg.*
import org.futurerobotics.jargon.math.MotionOnly
import org.futurerobotics.jargon.math.MotionState
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.ValueMotionState
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.mechanics.NominalDriveModel
import org.futurerobotics.jargon.pathing.trajectory.Trajectory
import org.futurerobotics.jargon.running.SuspendLoopSystemRunner
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.asFrequencyRegulator
import org.futurerobotics.jargon.statespace.*

/**
 * Autonomous drive. Feed it [trajectories] and it will follow it.
 */
class AutoDrive(motors: MotorsBlock, gyro: GyroBlock) : TickerSystem {

    companion object {
        //pid coeffs for position controller.
        val xCoeff = PidCoefficients(1.0, 0.1, 0.1)
        val yCoeff = PidCoefficients(1.0, 0.1, 0.1)
        val hCoeff = PidCoefficients(1.0, 0.1, 0.1)
    }

    val trajectories = ExternalQueue<Trajectory>()
    private val system = buildBlockSystem {
        val follower =
            TimeOnlyMotionProfileFollower<MotionState<Pose2d>>(
                ValueMotionState.ofAll(Pose2d.ZERO)
            ).apply {
                profileInput from trajectories.output
            }
        val positionController = HolonomicPidBotPoseController(
            this,
            xCoeff,
            yCoeff,
            hCoeff
        ).apply {
            reference from follower.output
        }

        VelocityController(this, SYSTEM_PERIOD).apply {
            motionReference from positionController.signal
            voltageSignal into motors.motorVolts
            velocityMeasurement from motors.motorVelocities
        }
        EncoderAndStrictGyroLocalizer(
            this,
            driveModel
        ).apply {
            headingMeasurement from gyro.headingMeasurement
            motorPositions from motors.motorPositions

            globalPose into positionController.state
        }
    }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            SuspendLoopSystemRunner(system, ticker.listener(5).asFrequencyRegulator()).runSuspend()
        }
    }
}

fun botVelocityStateSpaceSystem(
    driveModel: NominalDriveModel,
    period: Double
): StateSpaceRunner {
    val continuousMatrices = DriveStateSpaceModels.poseVelocityController(driveModel, driveModel)
    val qrCost = QRCost(
        listOf(
            4 * inches / s,
            4 * inches / s,
            0.5 * radians / s
        )
            .map { 1 / (it * it) }
            .let { diagMat(it) },
        (5 * volts)
            .let { 1 / (it * it) }
            .let { idenMat(4) * it }
    )
    val discreteMatrices = continuousMatrices.discretize(period)
    val k = continuousLQR(continuousMatrices, qrCost)
    val kff = plantInversion(discreteMatrices)
    //augment u-error
    val (A, B, C) = continuousMatrices
    val aAug = concat2x2dynamic(A, B, 0, 0)
    val bAug = concatCol(B, zeroMat(4, 4))
    val cAug = concatRow(C, zeroMat(4, 4))
    val kAug = concatRow(k, idenMat(4) * 0.0) * 0.0
    val kffAug = concatRow(kff, idenMat(4))
    val augmentedMatrices = ContinuousStateSpaceMatrices(aAug, bAug, cAug).discretize(period)
    val stateModifier = object : StateModifier {
        private val zeroVec = zeroVec(4)
        override fun augmentInitialState(x: Vec, prevXAug: Vec?): Vec {
            return if (prevXAug == null) x.append(zeroVec)
            else x.append(prevXAug[3 until 3 + 4])
        }

        override fun augmentReference(r: Vec): Vec {
            return r.append(zeroVec)
        }

        override fun deAugmentState(xAug: Vec): Vec {
            return xAug
//            return xAug[0 until 4]
        }
    }
    val noiseCovariance = NoiseCovariance(
        zeroMat(7, 7).apply {
            //guesstimates. Approximate.
            this[0, 0] = idenMat(2) * (5 * `in` / s / s * period)
            this[2, 2] = 10 * deg / s / s * period
            this[3, 3] = idenMat(4) * (6.0 * volts / s / s * period)
        },
        idenMat(4) * (20 * deg / s * period) //not due to measurement, but slippage.
    )
    val initialProcessCovariance = zeroMat(7, 7).apply {
        this[0, 0] = idenMat(2) * 0.0 //we pretty sure velocity is 0.
        this[2, 2] = 0.0
        this[3, 3] = idenMat(4) * (1.0 * period)
    }
    return StateSpaceRunnerBuilder()
        .addStateModifier(stateModifier)
        .setMatrices(augmentedMatrices)
        .addGainController(kAug)
        .addReferenceTracking(kffAug)
        .addSignalModifier(SignalLimiter(-11.0, 11.0))
        .addKalmanFilter {
            setNoiseCovariance(noiseCovariance)
            setInitialProcessCovariance(initialProcessCovariance)
        }.build()
}

class VelocityController(builder: BlockArrangementBuilder, period: Double) {
    val motionReference: Block.Input<MotionOnly<Pose2d>>
    val voltageSignal: Block.Output<Vec>
    val velocityMeasurement: Block.Input<Vec>

    init {
        with(builder) {
            val toState = BotMotionToVecVelState()
            motionReference = toState.input
            val velState = toState.output
            StateSpaceRunnerBlock(
                botVelocityStateSpaceSystem(
                    driveModel,
                    period
                ), zeroVec(3)
            ).also {
                it.referenceMotionState from velState
                this@VelocityController.voltageSignal = it.signal
                velocityMeasurement = it.measurement
            }
        }
    }
}
