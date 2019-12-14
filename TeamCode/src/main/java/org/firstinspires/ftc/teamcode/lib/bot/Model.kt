package org.firstinspires.ftc.teamcode.lib.bot

import org.firstinspires.ftc.teamcode.lib.SYSTEM_PERIOD
import org.firstinspires.ftc.teamcode.lib.bot.DriveModel.driveModel
import org.futurerobotics.jargon.linalg.*
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.mechanics.FixedWheelDriveModel
import org.futurerobotics.jargon.mechanics.MotorModel
import org.futurerobotics.jargon.mechanics.TransmissionModel
import org.futurerobotics.jargon.statespace.*
import kotlin.math.pow
import kotlin.math.sqrt

//models the bot. Used in [Drive]

object DriveModel {
    private val wheelMotorModel = MotorModel.fromMotorData(
        12 * volts,
        260 * ozf * `in`,
        9.2 * A,
        435 * rev / mins,
        0.25 * A
    )
    private val wheelTransmission = TransmissionModel.fromTorqueMultiplier(wheelMotorModel, 2.0, 0.0, 0.9)
    private const val botMass = 33.4 * lbs
    private val moi = botMass / 3 * (16 * `in`).pow(2) //todo: find out empirically
    val driveModel = FixedWheelDriveModel.mecanumLike(
        botMass,
        moi,
        wheelTransmission,
        2 * `in` / sqrt(2.0),
        8 * `in`,
        7 * `in`
    )
}

object BotVelocityStateSpaceModel {
    private val kAug: Mat
    private val kffAug: Mat
    private val augmentedMatrices: DiscreteStateSpaceMatrices
    private val stateModifier: StateModifier
    private val noiseCovariance: NoiseCovariance
    private val initialProcessCovariance: Mat

    init {
        val period = SYSTEM_PERIOD
        val continuousMatrices = DriveStateSpaceModels.poseVelocityController(driveModel, driveModel)
        val qrCost = QRCost(
            listOf(
                5 * inches / s,
                5 * inches / s,
                1 * radians / s
            )
                .map { 1 / (it * it) }
                .let { diagMat(it) },
            (6 * volts)
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

        kAug = concatRow(k, idenMat(4) * 0.0) * 0.0
        kffAug = concatRow(kff, idenMat(4))
        augmentedMatrices = ContinuousStateSpaceMatrices(aAug, bAug, cAug).discretize(period)
        stateModifier = object : StateModifier {
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
        noiseCovariance = NoiseCovariance(
            zeroMat(7, 7).apply {
                //guesstimates. Approximate.
                this[0, 0] = idenMat(2) * (5 * `in` / s / s * period)
                this[2, 2] = 10 * deg / s / s * period
                this[3, 3] = idenMat(4) * (6.0 * volts / s / s * period)
            },
            idenMat(4) * (20 * deg / s * period) //not due to measurement, but slippage.
        )
        initialProcessCovariance = zeroMat(7, 7).apply {
            this[0, 0] = idenMat(2) * 0.0 //we pretty sure velocity is 0.
            this[2, 2] = 0.0
            this[3, 3] = idenMat(4) * (1.0 * period)
        }
    }

    fun getRunner(): StateSpaceRunner = with(BotVelocityStateSpaceModel) {
        StateSpaceRunnerBuilder()
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
}


