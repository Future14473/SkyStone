package org.firstinspires.ftc.teamcode.lib.bot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.blocks.buildBlockSystem
import org.futurerobotics.jargon.blocks.control.MotorsBlock
import org.futurerobotics.jargon.blocks.control.PidCoefficients
import org.futurerobotics.jargon.blocks.control.PidController
import org.futurerobotics.jargon.blocks.functional.Monitor
import org.futurerobotics.jargon.linalg.Vec
import org.futurerobotics.jargon.linalg.genVec
import org.futurerobotics.jargon.linalg.get
import org.futurerobotics.jargon.linalg.size
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.math.intervalTo
import org.futurerobotics.jargon.running.SuspendLoopSystemRunner
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.asFrequencyRegulator

/**
 * The lift system. Uses PID.
 *
 * May use more fancy stuff in the future.
 */
class Lift(private val motors: MotorsBlock) : TickerSystem {

    companion object {
        val bounds = 0.0..1.5
        private const val spoolRadius = 1.25 * `in` / 2 //oh, it was diameter. whoops.
        private val pidCoefficients =
            PidCoefficients(
                1.0,
                0.05,
                0.1,
                -8.0 intervalTo 8.0,
                -6.0 intervalTo 11.0,
                3.0,
                4.0
            )
        private const val feedForward = 1 //always added to output, to try and compensate for gravity
    }

    val targetHeight = BoundedPositionBlock(0.0, bounds)
    val actualHeight = Monitor<Double>()
    private val system = buildBlockSystem {
        val controllers = List(motors.numMotors) { i ->
            PidController(pidCoefficients).apply {
                reference from targetHeight.output.pipe { it / spoolRadius }
                state from motors.motorPositions.pipe { it[i] }
            }
        }
        motors.motorVolts from generate {
            genVec(motors.numMotors) {
                controllers[it].signal.get + feedForward
            }
        }
        actualHeight.input from motors.motorPositions.pipe { it.avg() * spoolRadius }
//        runBlock {
//            RobotLog.v("Running")
//            RobotLog.v("Target: ${target.value}")
//            RobotLog.v("State: ${motors.motorPositions.get}")
//            RobotLog.v("Volts: ${motors.motorVolts.source()!!.get}")
//        }
    }

    private fun Vec.avg(): Double {
        var sum = 0.0
        repeat(size) { i ->
            sum += this[i]
        }
        return sum / size
    }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            SuspendLoopSystemRunner(system, ticker.listener(5).asFrequencyRegulator()).runSuspend()
        }
    }
}
