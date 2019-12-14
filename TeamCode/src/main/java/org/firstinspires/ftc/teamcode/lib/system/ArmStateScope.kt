package org.firstinspires.ftc.teamcode.lib.system

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.firstinspires.ftc.teamcode.lib.bot.Arm
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.TickerListener
import org.futurerobotics.jargon.running.syncedLoop
import kotlin.math.abs
import kotlin.math.roundToLong

const val ARM_READY = -75 * deg
const val ARM_READY_PLUS = -55 * deg
val ARM_GRAB = Arm.range.start
const val ARM_UP = -20 * deg
const val ARM_FORWARD = 90 * deg
val ARM_MAX = Arm.range.endInclusive

const val LIFT_DOWN_TOLERANCE = 3 * cm

abstract class ArmStateScope(system: RobotSystem) : RobotSystem by system, TickerSystem {

    //ticker
    lateinit var listener: TickerListener
    var armAngle: Double
        get() = arm.position
        set(value) {
            arm.position = value
        }
    var targetLiftHeight: Double
        get() = lift.targetHeight.position
        set(value) {
            lift.targetHeight.position = value
        }
    val actualLiftHeight: Double get() = lift.actualHeight.value ?: 0.0


    suspend inline fun loop(block: () -> Unit): Nothing {
        listener.syncedLoop {
            update()
            block()
            false
        }
        throw AssertionError()
    }

    /** Includes [update] */
    suspend inline fun waitUntil(condition: () -> Boolean) {
        while (!condition()) {
            listener.awaitNextTick()
            update()
        }
    }

    /**
     * Should run every loop.
     */
    abstract suspend fun update()

    /** Includes [update] */
    suspend inline fun pause(millis: Long) {
        val startMillis = System.nanoTime()
        val deadline = startMillis + millis * 1_000_000
        waitUntil {
            System.nanoTime() - deadline > 0
        }
    }

    suspend inline fun moveArmToAndWait(position: Double) {
        val pastPosition = armAngle
        armAngle = position
        pause((abs(pastPosition - position) * 250).roundToLong())
    }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            listener = ticker.listener(0)
            runSystem()
        }
    }

    protected abstract suspend fun CoroutineScope.runSystem()
}
