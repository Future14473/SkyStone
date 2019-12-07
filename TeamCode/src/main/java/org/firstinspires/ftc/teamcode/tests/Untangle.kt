package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import kotlinx.coroutines.delay
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode

@TeleOp
@Disabled
class Untangle : CoroutineOpMode() {

    override suspend fun runOpMode() {
        val motors = List(4) { hardwareMap.get(DcMotor::class.java, it.toString()) }
        var index = 0
        waitForStart()
        while (opModeIsActive()) {
            delay(100)
            val use = gamepad1.run { listOf(a, b, x, y) }
            use.forEachIndexed { i, b ->
                if (b) {
                    motors[index].power = 0.0
                    index = i
                    return@forEachIndexed
                }
            }
            motors[index].power = (gamepad1.left_stick_y / 2).toDouble()
            telemetry.addLine("Running $index")
            telemetry.update()
        }
    }
}
