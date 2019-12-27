package org.firstinspires.ftc.teamcode.lib.bot

import com.qualcomm.robotcore.hardware.Gamepad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.SYSTEM_PERIOD
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.blocks.buildBlockSystem
import org.futurerobotics.jargon.blocks.control.EncoderAndStrictGyroLocalizer
import org.futurerobotics.jargon.blocks.control.GyroBlock
import org.futurerobotics.jargon.blocks.control.MotorsBlock
import org.futurerobotics.jargon.blocks.functional.ExternalValue
import org.futurerobotics.jargon.math.MotionOnly
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.running.SuspendLoopSystemRunner
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.asFrequencyRegulator


/**
 * Manual drive. Need to set [targetVelocity].
 */
class ManualDrive(motors: MotorsBlock, gyro: GyroBlock, private val gamepad: Gamepad) : TickerSystem {

    val targetVelocity = ExternalValue(Pose2d.ZERO)

    private val system = buildBlockSystem {
        VelocityController(this, SYSTEM_PERIOD).apply {
            motionReference from targetVelocity.output.pipe { MotionOnly(it, Pose2d.ZERO) }
            voltageSignal into motors.motorVolts
            velocityMeasurement from motors.motorVelocities
        }

        EncoderAndStrictGyroLocalizer(this, driveModel).apply {
            headingMeasurement from gyro.headingMeasurement
            motorPositions from motors.motorPositions
        }
    }


    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            SuspendLoopSystemRunner(system, ticker.listener(5).asFrequencyRegulator()).runSuspend()
        }
    }
}
