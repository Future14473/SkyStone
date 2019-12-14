package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.field.getFieldGraph
import org.firstinspires.ftc.teamcode.lib.field.startLoading
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.convert.*

@TeleOp
@Disabled
class AutoTestMoveToCenter : BotSystemsOpMode(BotSystems.AutoDrive) {

    override suspend fun RobotSystem.onInit() {
        autoDrive.setPose(startLoading)
    }

    override suspend fun RobotSystem.onStart() {
//        val path = Line(Vector2d.ZERO, Vector2d(1.0,0.0)).addHeading(TangentHeading)

        autoDrive.followPath(getFieldGraph().getPath("startLoading", "center"))
        autoDrive.followPath(getFieldGraph().getPath("center", "startLoading"))
    }
}

@TeleOp
@Disabled
class AutoTestCorrect : BotSystemsOpMode(BotSystems.AutoDrive) {

    override suspend fun RobotSystem.onInit() {
        autoDrive.setPose(Pose2d(-0.5, -0.5, 90 * deg))
    }
}

