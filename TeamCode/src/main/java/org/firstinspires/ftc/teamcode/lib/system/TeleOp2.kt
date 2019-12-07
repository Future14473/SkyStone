package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.hardware.Gamepad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.syncedLoop

const val MAX_SPEED = 1.3 * m / s
const val MAX_ANG_SPEED = 3.0 * rad / s
const val MAX_SLOW_FACTOR = .2


class TeleOp2(system: IRobotSystem) : IRobotSystem by system, TickerSystem {
    private val gamepad: Gamepad = opMode.gamepad1

    private fun getPoseVelocity(): Pose2d =
        if (gamepad.right_trigger > 0) Pose2d.ZERO
        else {
            val x = -gamepad.right_stick_y * MAX_SPEED //UP is +x
            val y = -gamepad.right_stick_x * MAX_SPEED
            val heading = gamepad.left_stick_x * MAX_ANG_SPEED
            Pose2d(x, y, heading) * (1.0 - gamepad.left_trigger * (1 - MAX_SLOW_FACTOR))
        }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch {
            ticker.listener(0).syncedLoop {
                manualDrive.targetVelocity.value = getPoseVelocity()
                false
            }
        }
    }
}
