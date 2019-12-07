package org.firstinspires.ftc.teamcode.lib.bot

import com.qualcomm.hardware.bosch.BNO055IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.futurerobotics.jargon.hardware.Gyro

/**
 * A [Gyro] using an [imu].
 *
 * Maybe more stuff later. Thats it for now.
 *
 * ***NOT INITIALIZED ON START***
 */
class IMUGyro(private val imu: BNO055IMU) : Gyro {

    companion object {
        private val imuParams = BNO055IMU.Parameters().apply {
            mode = BNO055IMU.SensorMode.GYRONLY
            angleUnit = BNO055IMU.AngleUnit.RADIANS
            loggingEnabled = false
        }
    }

    fun initialize() {
        imu.initialize(imuParams)
    }

    fun isInitialized() = imu.isGyroCalibrated

    override val currentAngle: Double
        get() = -imu.getAngularOrientation(
            AxesReference.INTRINSIC,
            AxesOrder.ZYX,
            AngleUnit.RADIANS
        ).firstAngle.toDouble()
}
