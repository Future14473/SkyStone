package org.firstinspires.ftc.teamcode.lib.bot

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.futurerobotics.jargon.ftcbridge.FtcMotor
import org.futurerobotics.jargon.ftcbridge.FtcServo
import org.futurerobotics.jargon.math.convert.*
import org.openftc.revextensions2.ExpansionHubEx
import com.qualcomm.robotcore.hardware.Servo as LibServo

/**
 * Contains hardware. May have `null` values if things don't exist.
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
class Hardware(map: HardwareMap) {

    companion object {
        private const val imuName = "imu"

        private val wheelConfigs = kotlin.run {
            val tpr = 383.6
            listOf(
                MotorConfig("FrontLeft", Direction.REVERSE, tpr),
                MotorConfig("FrontRight", Direction.FORWARD, tpr),
                MotorConfig("BackLeft", Direction.REVERSE, tpr),
                MotorConfig("BackRight", Direction.FORWARD, tpr)
            )
        }
        //0 deg is up, 90 deg is out, negatives is in.
        private val armConfigs = listOf(
            RangeServoConfig("ArmLeft", 0.46..0.11, 0.0..90 * deg),
            RangeServoConfig("ArmRight", 0.49..0.84, 0.0..90 * deg)
        )

        private val liftConfigs = kotlin.run {
            val tpr = 537.6
            listOf(
                MotorConfig("LiftLeft", Direction.REVERSE, -tpr),
                MotorConfig("LiftRight", Direction.FORWARD, tpr)
            )
        }

        private const val clawName = "Claw"

        private val intakeNames = listOf("IntakeLeft", "IntakeRight")

        private const val grabberName = "Grabber"

        private const val flickerName = "Flicker"
    }

    constructor(opMode: OpMode) : this(opMode.hardwareMap)

    val hubs = map.getAll(ExpansionHubEx::class.java)

    init {
        if (hubs.size != 2) {
            error("CANNOT FIND THE HUBS.")
        }
    }

    val imu = map.tryGet(BNO055IMU::class.java, imuName)
    val wheelMotors = wheelConfigs.getFrom(map)
    val armServos = armConfigs.getFrom(map)
    val liftMotors = liftConfigs.getFrom(map)
    val clawServo = map.tryGet(LibServo::class.java, clawName)
    val intakeMotors = intakeNames.map { map.tryGet(DcMotor::class.java, it) }
    val grabberServo = map.tryGet(LibServo::class.java, grabberName)
    val flickerServo = map.tryGet(LibServo::class.java, flickerName)

    fun enableCharging() {
        hubs.forEach {
            it.isPhoneChargeEnabled = true
        }
    }
}

data class MotorConfig(
    val name: String,
    val direction: Direction,
    val ticksPerRev: Double
) {

    fun tryGetFromHardwareMap(map: HardwareMap): FtcMotor? {
        val motor = map.tryGet(DcMotorEx::class.java, name) ?: return null
        motor.direction = direction
        return FtcMotor(motor, ticksPerRev)
    }
}

data class RangeServoConfig(
    val name: String,
    val servoRange: ClosedFloatingPointRange<Double>,
    val angleRange: ClosedFloatingPointRange<Double>
) {

    fun tryGetFromHardwareMap(map: HardwareMap): FtcServo? {
        val servo = map.tryGet(LibServo::class.java, name) ?: return null
        return FtcServo(servo, servoRange, angleRange)
    }
}

@JvmName("getFromMotors") //disambiguate with below
fun List<MotorConfig>.getFrom(map: HardwareMap) = map { it.tryGetFromHardwareMap(map) }

fun MotorConfig.getFrom(map: HardwareMap) = tryGetFromHardwareMap(map)

fun List<RangeServoConfig>.getFrom(map: HardwareMap) = map { it.tryGetFromHardwareMap(map) }

