package org.firstinspires.ftc.teamcode.lib.bot

import com.qualcomm.hardware.bosch.BNO055IMU
import org.futurerobotics.jargon.hardware.Gyro

/**
 * A [Gyro] using an [imu].
 *
 * Maybe more stuff later. Thats it for now.
 *
 * ***NOT INITIALIZED ON START***
 */
class IMUGyro(val imu: BNO055IMU) : Gyro {

    companion object {
        private val imuParams = BNO055IMU.Parameters().apply {
            angleUnit = BNO055IMU.AngleUnit.RADIANS
            loggingEnabled = false
        }
    }

    fun initialize() {
        imu.initialize(imuParams)
    }

    fun isInitialized() = imu.isGyroCalibrated

    override val currentAngle: Double
        get() = imu.angularOrientation.firstAngle.toDouble()
}
