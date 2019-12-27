package org.firstinspires.ftc.teamcode.actual

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import kotlinx.coroutines.delay
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.field.genParams
import org.firstinspires.ftc.teamcode.lib.field.tile
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.Vector2d
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.pathing.ConstantHeading
import org.futurerobotics.jargon.pathing.Line
import org.futurerobotics.jargon.pathing.LinearlyInterpolatedHeading
import org.futurerobotics.jargon.pathing.addHeading
import org.futurerobotics.jargon.pathing.trajectory.MaxTotalAccelConstraint
import org.futurerobotics.jargon.pathing.trajectory.MaxVelConstraint
import org.futurerobotics.jargon.pathing.trajectory.MotionConstraintSet
import kotlin.math.PI

@Autonomous
class JustGrabFoundationAuto : BotSystemsOpMode(BotSystems.AutoDrive) {

    override suspend fun RobotSystem.onInit() {
        autoDrive.resetInitialPose(Pose2d(0.0, 0.0, PI))
        autoDrive.isFlipped = false //red side -> true
    }

    override suspend fun RobotSystem.onStart() {
        val start = Vector2d.ZERO
        val preGrab = Vector2d(1.2 * tile, .8 * tile)
        val grab = preGrab + Vector2d(0.3 * tile, 0.0)
        val back = Vector2d(-0.5 * tile, -0.2 * tile)
        val end = Vector2d(-0.5 * tile, -2.0 * tile)


        val heading = ConstantHeading(PI)
        val path1 = Line(start, preGrab).addHeading(heading)
        val slowConstraints = MotionConstraintSet(
            MaxVelConstraint(0.2),
            MaxTotalAccelConstraint(0.5)
        )
        val path11 = slowConstraints.generateTrajectory(Line(preGrab, grab).addHeading(heading), genParams)
        val path2 = Line(grab, back).addHeading(LinearlyInterpolatedHeading(PI, PI + 80 * deg))
        val path3 = Line(back, end).addHeading(LinearlyInterpolatedHeading(PI + 80 * deg, PI))

        val preGrabJob = autoDrive.followPath(path1)
        val grabJob = autoDrive.followTraj(path11)
        preGrabJob.join()
        delay(500)
        grabber.close()
        grabJob.join()
        delay(300)
        autoDrive.followPath(path2).join()
        grabber.open()
        delay(300)
        autoDrive.followPath(path3).join()

        requestOpModeStop()
    }
}
