package org.firstinspires.ftc.teamcode.lib.bot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.blocks.buildBlockSystem
import org.futurerobotics.jargon.blocks.control.MotorsBlock
import org.futurerobotics.jargon.blocks.functional.ExternalValue
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.ValueMotionOnly
import org.futurerobotics.jargon.running.SuspendLoopSystemRunner
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.asFrequencyRegulator


/**
 * Manual drive. Need to set [targetVelocity].
 */
class ManualDrive(motors: MotorsBlock) : TickerSystem {

    val targetVelocity = ExternalValue(Pose2d.ZERO)

    private val system = buildBlockSystem {
        VelocityController(this).apply {
            motionReference from targetVelocity.output.pipe { ValueMotionOnly(it, Pose2d.ZERO) }
            voltageSignal into motors.motorVolts
            velocityMeasurement from motors.motorVelocities
        }

//        BoundedLocalizer(this, driveModel).apply {
//            headingMeasurement from gyro.headingMeasurement
//            motorPositions from motors.motorPositions
//        }
    }


    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            SuspendLoopSystemRunner(system, ticker.listener(5).asFrequencyRegulator()).runSuspend()
        }
    }
}
