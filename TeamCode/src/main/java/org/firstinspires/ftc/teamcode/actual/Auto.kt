package org.firstinspires.ftc.teamcode.actual

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import kotlinx.coroutines.delay
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.field.tile
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.firstinspires.ftc.teamcode.tests.AutoBeingTested
import org.futurerobotics.jargon.math.Vector2d
import org.futurerobotics.jargon.pathing.ConstantHeading
import org.futurerobotics.jargon.pathing.Line
import org.futurerobotics.jargon.pathing.TangentHeading
import org.futurerobotics.jargon.pathing.addHeading

@Autonomous(name = "LOADING BLUE AUTO")
class BlueLoading : AutoBeingTested(false)

@Autonomous(name = "LOADING RED AUTO")
class RedLoading : AutoBeingTested(true)

@Autonomous
class JustMoveForward : BotSystemsOpMode(BotSystems.AutoDrive){

    override suspend fun RobotSystem.onStart() {
        val path = Line(Vector2d.ZERO, Vector2d(1.5* tile, 0.0)).addHeading(TangentHeading)
        autoDrive.followPath(path).join()
        delay(300)
        requestOpModeStop()
    }
}

@Autonomous
class JustMoveForwardLeftDiagonal: BotSystemsOpMode(BotSystems.AutoDrive) {

    override suspend fun RobotSystem.onStart() {
        val path = Line(Vector2d.ZERO, Vector2d(1.5 * tile, 1.5*tile)).addHeading(ConstantHeading(0.0))
        autoDrive.followPath(path).join()
        delay(300)
        requestOpModeStop()
    }
}
