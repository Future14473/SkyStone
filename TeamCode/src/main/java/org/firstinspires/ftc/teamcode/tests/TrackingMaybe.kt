package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.*
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.field.genParams
import org.firstinspires.ftc.teamcode.lib.field.motionConstraints
import org.firstinspires.ftc.teamcode.lib.field.tile
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.firstinspires.ftc.teamcode.lib.withScope
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.Vector2d
import org.futurerobotics.jargon.pathing.Line
import org.futurerobotics.jargon.pathing.LinearlyInterpolatedHeading
import org.futurerobotics.jargon.pathing.addHeading
import org.futurerobotics.jargon.pathing.trajectory.Trajectory
import kotlin.math.PI

@UseExperimental(ExperimentalCoroutinesApi::class)
@TeleOp
@Disabled
class TrackingMaybe : BotSystemsOpMode(BotSystems.ImageTracker, BotSystems.AutoDrive) {

    private lateinit var trajs: Deferred<List<Trajectory>>
    override suspend fun RobotSystem.onInit() = withScope {
        autoDrive.resetInitialPose(Pose2d(Vector2d(-2 * tile, 2 * tile), PI/2))
        trajs = async {
            listOf(
                Line(Vector2d(-2 * tile, 2 * tile), Vector2d(2 * tile, 2 * tile))
                    .addHeading(LinearlyInterpolatedHeading(PI / 2, 3 * PI / 2)),
                Line(Vector2d(2 * tile, 2 * tile), Vector2d(-2 * tile, 2 * tile))
                    .addHeading(LinearlyInterpolatedHeading(3 * PI / 2 + 0.01, PI / 2))
            ).map {
                motionConstraints.generateTrajectory(it, genParams)
            }
        }
    }

    override suspend fun RobotSystem.onStart() = coroutineScope<Unit> {
        launch {
            val poses = imageTracker.poseChannel.openSubscription()
            for (pose in poses) { //receive forever
                autoDrive.setPose(pose)
            }

            //problem: not much a way to determine order of running.
        }
        launch {
            val trajectories = trajs.await()
            while (true) {
                trajectories.forEach {
                    autoDrive.followTraj(it).join()
                }
            }
        }
    }
}

