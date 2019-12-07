package org.firstinspires.ftc.teamcode.lib.bot

import org.futurerobotics.jargon.blocks.PrincipalOutputBlock


/**
 * Interface to represent something that can set [position] with [bounds].
 */
interface BoundedPosition {

    val bounds: ClosedFloatingPointRange<Double>
    var position: Double
}

class BoundedPositionBlock(initialPosition: Double, override val bounds: ClosedFloatingPointRange<Double>) :
    PrincipalOutputBlock<Double>(Processing.LAZY), BoundedPosition {

    @Volatile
    override var position: Double = initialPosition
        set(value) {
            field = value.coerceIn(bounds)
        }

    override fun Context.getOutput(): Double = position
}

//todo: useless? only used in intake.
interface SetPower {

    var power: Double
}
