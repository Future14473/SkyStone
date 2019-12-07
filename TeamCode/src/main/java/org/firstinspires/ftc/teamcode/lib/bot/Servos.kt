package org.firstinspires.ftc.teamcode.lib.bot

import com.qualcomm.robotcore.hardware.Servo

//constants for various servo doors.

val CLAW_RANGE = 0.0..1.0
val GRABBER_RANGE = 0.5..0.5
val FLICKER_RANGE = 0.5..0.5


/**
 * Simple wrapper around a [servo] where you can change [isOpen] and it will go between [openPosition] and [closedPosition].
 */
class ServoDoor(
    private val servo: Servo,
    private val openCloseRange: ClosedFloatingPointRange<Double>,
    initialOpen: Boolean = false
) {

    var isOpen: Boolean = false
        set(open) {
            servo.position = if (open) openCloseRange.start else openCloseRange.endInclusive

            field = open
        }

    fun close() {
        isOpen = false
    }

    fun open() {
        isOpen = true
    }

    init {
        this.isOpen = initialOpen
    }
}
