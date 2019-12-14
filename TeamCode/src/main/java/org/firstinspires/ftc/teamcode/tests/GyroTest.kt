package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystemImpl
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode

@TeleOp
@Disabled
class GyroTest : CoroutineOpMode() {

    override suspend fun runOpMode() = coroutineScope {
        val bot = RobotSystemImpl(this@GyroTest, BotSystems.Gyro)
        val gyro = bot.gyro
        waitForStart()
        while (opModeIsActive()) {
            delay(100)
            val angle = gyro.imu.angularOrientation.toAngleUnit(AngleUnit.DEGREES)
            telemetry.run {
                addLine("angle: $angle")
                update()
            }
        }
    }
}
