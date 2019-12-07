package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.hardware.Gamepad

/**
 * Utility for button states. So we can say stuff like `isClicked`.
 */
class Buttons(private val pad: Gamepad) {

    private val buttons = mutableListOf<Button>()

    val a = Button()
    val b = Button()
    val x = Button()
    val y = Button()

    val left_bumper = Button()
    val right_bumper = Button()

    val dpad_up = Button()
    val dpad_down = Button()
    val dpad_left = Button()
    val dpad_right = Button()

    val left_stick_button = Button()
    val right_stick_button = Button()

    private fun getStates() = pad.run {
        booleanArrayOf(
            a,
            b,
            x,
            y,
            left_bumper,
            right_bumper,
            dpad_up,
            dpad_down,
            dpad_left,
            dpad_right,
            left_stick_button,
            right_stick_button
        )
    }

    fun update() {
        val states = getStates()
        buttons.forEachIndexed { index, button ->
            button.update(states[index])
        }
    }

    inner class Button {
        init {
            buttons += this
        }

        var isPressed: Boolean = false
            private set
        val isReleased get() = !isPressed

        /** This starts at 1 */
        var pressedFor: Int = 0

        val isClicked get() = isPressed && pressedFor == 1
        val isUnclicked get() = !isPressed && pressedFor == 1

        internal fun update(isPressed: Boolean) {
            val previous = this.isPressed
            this.isPressed = isPressed
            if (isPressed != previous) {
                pressedFor = 0
            }
            pressedFor++
        }
    }
}
