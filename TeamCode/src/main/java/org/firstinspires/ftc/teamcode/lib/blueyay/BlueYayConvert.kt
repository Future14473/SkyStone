package org.firstinspires.ftc.teamcode.lib.blueyay

import org.futurerobotics.bluejay.original.localizers.PoseOrientation
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.math.convert.*


/**
 * Converts from mm to m, deg to rad.
 */
fun PoseOrientation.toPose2d(): Pose2d = Pose2d(x * mm, y * mm, rot * degrees)
