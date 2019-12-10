package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.hardware.Gamepad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.TickerListener
import org.futurerobotics.jargon.running.syncedLoop
import kotlin.math.abs
import kotlin.math.roundToLong

//scroll alll the way down for TeleOp1

//additional teleOp
fun TeleOp1.additional() {
    val intakeSignal = (gamepad.right_trigger - gamepad.left_trigger / 2).toDouble()
    intake.power = intakeSignal
}

@UseExperimental(ExperimentalCoroutinesApi::class)
class TeleOp1(system: IRobotSystem) : IRobotSystem by system, TickerSystem {

    var state: ArmState = ArmState.Ready

    //controls
    val gamepad: Gamepad = opMode.gamepad2
    private val buttons = Buttons(gamepad)

    val liftSignal get() = -gamepad.right_stick_y
    val armSignal get() = -gamepad.left_stick_y

    val grabSignal get() = buttons.right_bumper.isClicked
    val releaseSignal get() = buttons.left_bumper.isClicked

    val toFancySignal get() = buttons.a.isClicked
    val toOldSchoolSignal get() = buttons.b.isClicked
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

    var additionalIntakePower = 0.0


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
    fun update() {
        buttons.update()
        additional()
    }

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
            state = with(state) { run() }
        }
    }
}
