package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import kotlinx.coroutines.coroutineScope
import org.firstinspires.ftc.teamcode.lib.bot.Hardware
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.Buttons
import org.firstinspires.ftc.teamcode.lib.system.RobotSystemImpl
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.running.syncedLoop
import java.util.*

@TeleOp
//@Disabled
class SingleServoTest : CoroutineOpMode() {

    private val name = "ArmLeft"

    override suspend fun runOpMode() {
        val servo = hardwareMap.get(Servo::class.java, name)
        var position = 0.0

        waitForStart()
        while (opModeIsActive()) {
            sleep(100)
            val stick = gamepad1.left_stick_y.toDouble()

            position += stick / 20
            servo.position = position
            telemetry.addLine("name:     $name")
            telemetry.addLine("position: $position")
            telemetry.update()
        }
    }
}

@TeleOp
class SingleMotorTest : CoroutineOpMode() {


    override suspend fun runOpMode() {
        val allMotors = hardwareMap.getAll(DcMotorEx::class.java)
        val buttons = Buttons(gamepad1)
        var index = 0
        waitForStart()
        var motor = allMotors[index]
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.power = 0.0
        fun resetMotor() {
            motor.power = 0.0
            motor = allMotors[index]
            motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
            motor.power = 0.0
        }
        while (opModeIsActive()) {
            buttons.update()
            if (buttons.a.isClicked) {
                index = (index + 1) % allMotors.size
                resetMotor()
            }
            if (buttons.b.isClicked) {
                index = (index + allMotors.size - 1) % allMotors.size
                resetMotor()
            }

            if (gamepad1.x) {
                motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
            }
            val stick = -gamepad1.left_stick_y.toDouble()
            motor.power = stick
            telemetry.addLine("name:     ${hardwareMap.getNamesOf(motor).joinToString()}")
            telemetry.addLine("stick:    $stick")
            telemetry.addLine("position: ${motor.currentPosition}")
            telemetry.update()
        }
    }
}

@TeleOp
//@Disabled
class ArmServosTest : CoroutineOpMode() {

    override suspend fun runOpMode() {
        val arms = Hardware(this).armServos.requireNoNulls()
        waitForStart()
        var position = 0.0
        while (opModeIsActive()) {
            sleep(100)
            val stick = gamepad1.left_stick_y.toDouble()
            position = (position + stick * 5).coerceIn(-120.0, 120.0)
            arms.forEach {
                it.position = position * deg
            }
            telemetry.addLine("position: $position")
            telemetry.update()
        }
    }
}

@TeleOp
class LiftTest : CoroutineOpMode() {

    private lateinit var system: RobotSystemImpl

    override suspend fun runOpMode() = coroutineScope {
        system = RobotSystemImpl(this@LiftTest, EnumSet.of(BotSystems.Lift))
        val lift = system.lift
        val motors = system.hardware.liftMotors.requireNoNulls()
        waitForStart()
        motors.forEach {
            it.resetPosition()
        }
        system.start(this)
        var position = 0.0
        system.ticker.listener().syncedLoop {

            val stick = -gamepad1.left_stick_y.toDouble()
            position = (position + stick * 0.05).coerceIn(0.0, 2.0)
            lift.targetHeight.position = position
            telemetry.addLine("target: $position")
            telemetry.addLine("stick: $stick")
            telemetry.addLine("actual: ${system.lift.actualHeight}")
            motors.forEachIndexed { i, it ->
                telemetry.addLine("height $i: ${it.position}")
            }
            motors.forEachIndexed { i, it ->
                telemetry.addLine("encoder $i: ${it.motor.currentPosition}")
            }
            telemetry.update()

//            RobotLog.v("End of loop")
            false
        }
    }
}
