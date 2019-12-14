package org.firstinspires.ftc.teamcode.lib.system

import org.firstinspires.ftc.teamcode.lib.field.robot
import org.firstinspires.ftc.teamcode.lib.field.tile
import org.futurerobotics.jargon.blocks.Block
import org.futurerobotics.jargon.blocks.BlockArrangementBuilder
import org.futurerobotics.jargon.blocks.control.DeltaBasedLocalizer
import org.futurerobotics.jargon.blocks.control.MotorAndGyroToBotDelta
import org.futurerobotics.jargon.blocks.control.PoseLocalizer
import org.futurerobotics.jargon.linalg.Vec
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.mechanics.MotorBotVelInteraction

class BoundedLocalizer(
    builder: BlockArrangementBuilder, interaction: MotorBotVelInteraction
) : PoseLocalizer {

    override val poseOverride: Block.Input<Pose2d?>
    override val globalPose: Block.Output<Pose2d>
    val motorPositions: Block.Input<Vec>

    val headingMeasurement: Block.Input<Double>

    init {
        with(builder) {
            val deltaGetter = MotorAndGyroToBotDelta(interaction)
            motorPositions = deltaGetter.motorPositions
            headingMeasurement = deltaGetter.headingMeasurement

            val delta = deltaGetter.botDelta
            val tracker = BoundedBotDeltaLocalizer().apply {
                botDelta from delta
            }
            poseOverride = tracker.poseOverride
            globalPose = tracker.globalPose
        }
    }
}

private val bounds = (3 * tile - .5 * robot).let { -it..it }

open class BoundedBotDeltaLocalizer(initialPose: Pose2d = Pose2d.ZERO) : DeltaBasedLocalizer(initialPose) {

    /** The velocity input */
    val botDelta: Input<Pose2d> = newInput()

    override fun Context.getPoseDelta(): Pose2d = botDelta.get
    override fun Context.mapPose(pose: Pose2d): Pose2d {
        val vec = pose.vec
        return pose.copy(vec = pose.vec.copy(x = vec.x.coerceIn(bounds), y = vec.y.coerceIn(bounds)))
    }
}
