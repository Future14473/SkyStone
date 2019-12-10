package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.hardware.Gamepad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.syncedLoop

const val MAX_SPEED = 0.8 * m / s
const val MAX_ANG_SPEED = 1.5 * rad / s
const val MAX_SLOW_FACTOR = .4

private fun TeleOp1.run() {
    if (buttons.a.isClicked) grabber.close()
    if (buttons.b.isClicked) grabber.open()
    if (buttons.x.isClicked) directionMultiplier = -1 //intake side
    if (buttons.y.isClicked) directionMultiplier = 1
    manualDrive.targetVelocity.value = getPoseVelocity()
}

class TeleOp1(system: IRobotSystem) : IRobotSystem by system, TickerSystem {
    private val gamepad: Gamepad = opMode.gamepad1

    var directionMultiplier = -1 //start intake side

    fun getPoseVelocity(): Pose2d =
        if (gamepad.right_trigger > 0) Pose2d.ZERO
        else {
            val x = gamepad.right_stick_y * MAX_SPEED * directionMultiplier //UP is +x
            val y = gamepad.right_stick_x * MAX_SPEED * directionMultiplier
            val heading = -gamepad.left_stick_x * MAX_ANG_SPEED
            Pose2d(x, y, heading) * (1.0 - gamepad.left_trigger * (1 - MAX_SLOW_FACTOR))
        }

    val buttons = Buttons(gamepad)

    private fun update() {
        buttons.update()
    }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            grabber.open()
            ticker.listener(0).syncedLoop {
                update()
                run()
                false
            }
        }
    }
}


