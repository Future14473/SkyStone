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
    override val angle: Double
        get() = -imu.angularOrientation.firstAngle.toDouble()
    override val angularVelocity: Double
        get() = -imu.angularVelocity.zRotationRate.toDouble()
}
