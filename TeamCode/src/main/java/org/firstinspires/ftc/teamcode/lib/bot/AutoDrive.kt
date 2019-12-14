package org.firstinspires.ftc.teamcode.lib.bot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.firstinspires.ftc.teamcode.lib.bot.DriveModel.driveModel
import org.firstinspires.ftc.teamcode.lib.field.genParams
import org.firstinspires.ftc.teamcode.lib.field.motionConstraints
import org.firstinspires.ftc.teamcode.lib.system.BoundedLocalizer
import org.futurerobotics.jargon.blocks.Block
import org.futurerobotics.jargon.blocks.BlockArrangementBuilder
import org.futurerobotics.jargon.blocks.buildBlockSystem
import org.futurerobotics.jargon.blocks.control.*
import org.futurerobotics.jargon.blocks.functional.ExternalQueue
import org.futurerobotics.jargon.blocks.functional.Monitor
import org.futurerobotics.jargon.hardware.Gyro
import org.futurerobotics.jargon.linalg.Vec
import org.futurerobotics.jargon.linalg.zeroVec
import org.futurerobotics.jargon.math.MotionOnly
import org.futurerobotics.jargon.math.MotionState
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.ValueMotionState
import org.futurerobotics.jargon.pathing.Path
import org.futurerobotics.jargon.pathing.PointPath
import org.futurerobotics.jargon.pathing.TrajectoryCompleteCallback
import org.futurerobotics.jargon.pathing.TrajectoryWithCallbacks
import org.futurerobotics.jargon.pathing.trajectory.Trajectory
import org.futurerobotics.jargon.running.CompletableJobCallback
import org.futurerobotics.jargon.running.SuspendLoopSystemRunner
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.asFrequencyRegulator
import org.futurerobotics.jargon.util.replaceIf

/**
 * Autonomous drive. Feed it [trajectories], or [followPath], and it will go.
 */
class AutoDrive(motors: MotorsBlock, gyro: Gyro) : TickerSystem {

    companion object {
        val xCoeff = PidCoefficients(1.0, 0.02, 0.1)
        val yCoeff = PidCoefficients(1.0, 0.02, 0.1)
        val hCoeff = PidCoefficients(2.0, 0.02, 0.1)
    }


    @Volatile
    private var flipReference = false

    /**
     * Sets the side. If set to blue, references will have all y values flipped (map to other side of field).
     */
    fun setSide(side: Side) {
        flipReference = side.isFlipped
    }

    enum class Side(internal val isFlipped: Boolean) {
        Red(true),
        Blue(false)
    }

    val trajectories = ExternalQueue<TrajectoryWithCallbacks>()
    val currentPosition = Monitor<Pose2d>()

    private val gyroBlock = GyroBlock(gyro)
    private val poseOverrideQueue = ExternalQueue<Pose2d>()

    private fun Pose2d.flipPose(): Pose2d {
        return Pose2d(x, -y, -heading)
    }

    private val system = buildBlockSystem {
        val reference =
            TimeOnlyMotionProfileFollower<MotionState<Pose2d>>(
                ValueMotionState.ofAll(Pose2d.ZERO)
            ).apply {
                profileInput from trajectories.output
            }.output.pipe { ref ->
                ref.replaceIf(flipReference) {
                    ValueMotionState(
                        it.value.flipPose(),
                        it.deriv.flipPose(),
                        it.secondDeriv.flipPose()
                    )
                }
            }
        val positionController =
            HolonomicPidBotPoseController(this, xCoeff, yCoeff, hCoeff).apply {
                this.reference from reference
            }

        VelocityController(this).apply {
            motionReference from positionController.signal
            voltageSignal into motors.motorVolts
            velocityMeasurement from motors.motorVelocities
        }
        val localizer = BoundedLocalizer(this, driveModel).apply {
            headingMeasurement from gyroBlock.headingMeasurement
            motorPositions from motors.motorPositions
            globalPose into positionController.state
            poseOverride from poseOverrideQueue.output
        }
        currentPosition.input from localizer.globalPose

    }

    fun setPose(pose: Pose2d) {
        gyroBlock.initialHeading = pose.heading //kinda not necessary, but eh.
        poseOverrideQueue.clear()
        poseOverrideQueue += pose
    }

    fun followPath(path: Path): Job {
        return followTraj(motionConstraints.generateTrajectory(path, genParams))
    }

    fun followTraj(trajectory: Trajectory): Job {
        val job = Job()
        val callback = TrajectoryCompleteCallback(CompletableJobCallback(job))
        trajectories += TrajectoryWithCallbacks(trajectory, setOf(callback))
        return job
    }

    fun resetInitialPose(pose: Pose2d) {
        setPose(pose)
        followPath(PointPath(pose))
    }


    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            SuspendLoopSystemRunner(system, ticker.listener(5).asFrequencyRegulator()).runSuspend()
        }
    }
}

class VelocityController(builder: BlockArrangementBuilder) {
    val motionReference: Block.Input<MotionOnly<Pose2d>>
    val voltageSignal: Block.Output<Vec>
    val velocityMeasurement: Block.Input<Vec>

    init {
        with(builder) {
            val toState = BotMotionToVecVelState()
            motionReference = toState.input
            val velState = toState.output
            StateSpaceRunnerBlock(
                BotVelocityStateSpaceModel.getRunner(), zeroVec(3)
            ).also {
                it.referenceMotionState from velState
                voltageSignal = it.signal
                velocityMeasurement = it.measurement
            }
        }
    }
}
