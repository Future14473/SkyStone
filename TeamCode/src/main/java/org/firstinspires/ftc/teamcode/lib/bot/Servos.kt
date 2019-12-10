package org.firstinspires.ftc.teamcode.lib.bot

import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.RobotLog

//constants for various servo doors.

val CLAW_RANGE = 0.0..1.0
//TODO: find values
val GRABBER_RANGE = 0.35..0.0
val FLICKER_RANGE = 1.0..0.4


/**
 * Simple wrapper around a [servo] where you can change [isOpen] and it will go between a given [openCloseRange]
 */
class ServoDoor(
    private val servo: Servo,
    private val openCloseRange: ClosedFloatingPointRange<Double>,
    initialOpen: Boolean = false
) {

    var isOpen: Boolean = initialOpen
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
        //for some reason isOpen = initialOpen doesn't work
        RobotLog.d("Initial   to $initialOpen")
        servo.position = if (initialOpen) openCloseRange.start else openCloseRange.endInclusive
    }
}
