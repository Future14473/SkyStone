package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.hardware.Gamepad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.syncedLoop

const val MAX_SPEED = 1.5 * m / s
const val MAX_ANG_SPEED = 2.5 * rad / s
const val SLOW_SPEED_RATIO = .3

private fun TeleOp1.runSystem() {
    if (buttons.b.isClicked) grabber.close()
    if (buttons.a.isClicked) grabber.open()
    if (buttons.right_bumper.isClicked) directionMultiplier *= -1
    manualDrive.targetVelocity.value = getPoseVelocity()
}
private fun TeleOp1.getPoseVelocity(): Pose2d =
    if (gamepad.right_trigger > 0) Pose2d.ZERO
    else {
        val x = gamepad.right_stick_y * MAX_SPEED * directionMultiplier //UP is +x
        val y = gamepad.right_stick_x * MAX_SPEED * directionMultiplier
        val heading = -gamepad.left_stick_x * MAX_ANG_SPEED
        Pose2d(x, y, heading) * (SLOW_SPEED_RATIO + (1 - SLOW_SPEED_RATIO) * gamepad.left_trigger)
    }

class TeleOp1(system: RobotSystem) : RobotSystem by system, TickerSystem {
    val gamepad: Gamepad = opMode.gamepad1

    var directionMultiplier = -1 //start intake side

    val buttons = Buttons(gamepad)

    private fun update() {
        buttons.update()
    }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            ticker.listener(0).syncedLoop {
                update()
                runSystem()
                false
            }
        }
    }
}
