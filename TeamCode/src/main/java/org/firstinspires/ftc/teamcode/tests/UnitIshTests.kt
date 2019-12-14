package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.lib.bot.Hardware
import org.firstinspires.ftc.teamcode.lib.recoverScope
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystemImpl
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.running.syncedLoop
import java.util.*

@TeleOp
@Disabled
class SingleServoTest : CoroutineOpMode() {

    private val name = "Flicker"

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
@Disabled
class SingleMotorTest : CoroutineOpMode() {

    private val name = "LiftLeft"
    private val direction = DcMotorSimple.Direction.FORWARD

    override suspend fun runOpMode() {
        val motor = hardwareMap.get(DcMotorEx::class.java, name)
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.direction = direction
        waitForStart()
        while (opModeIsActive()) {
            if (gamepad1.x) {
                motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
            }
            val stick = gamepad1.left_stick_y.toDouble()
            motor.power = stick
            telemetry.addLine("name:     $name")
            telemetry.addLine("position: ${motor.currentPosition}")
            telemetry.update()
        }
    }
}

@TeleOp
@Disabled
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
@Disabled
class LiftTest : CoroutineOpMode() {

    private lateinit var system: RobotSystemImpl

    override suspend fun runOpMode() {
        system = RobotSystemImpl(this, EnumSet.of(BotSystems.Lift))
        val lift = system.lift
        val motors = system.hardware.liftMotors.requireNoNulls()
        waitForStart()
        motors.forEach {
            it.resetPosition()
        }
        system.start(recoverScope())
        var position = 0.0
        system.ticker.listener().syncedLoop {

            val stick = -gamepad1.left_stick_y.toDouble()
            position = (position + stick * 0.05).coerceIn(0.0, 2.0)
            lift.targetHeight.position = position
            telemetry.addLine("target: $position")
            telemetry.addLine("stick: $stick")
            motors.forEachIndexed { i, it ->
                telemetry.addLine("$i: ${it.position}")
            }
            telemetry.update()

//            RobotLog.v("End of loop")
            false
        }
    }
}
