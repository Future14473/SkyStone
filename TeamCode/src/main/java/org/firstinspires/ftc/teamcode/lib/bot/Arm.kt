package org.firstinspires.ftc.teamcode.lib.bot

import org.firstinspires.ftc.teamcode.lib.system.ArmState
import org.futurerobotics.jargon.ftcbridge.FtcServo
import org.futurerobotics.jargon.math.convert.*

/**
 * @see [ArmState]
 *
 * Basically has a [position] which can be set. Thats it.
 */
class Arm(private val servos: List<FtcServo>) : BoundedPosition {

    companion object {
        //maximum allowed range.
        val range = -120 * deg..140 * deg
    }

    override val bounds: ClosedFloatingPointRange<Double> = range

    override var position: Double
        get() = servos.sumByDouble { it.position } / 2
        set(value) {
            val pos = value.coerceIn(bounds)
            servos.forEach {
                it.position = pos
            }
        }
}
