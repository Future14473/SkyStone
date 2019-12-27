package org.firstinspires.ftc.teamcode.lib.field

import org.firstinspires.ftc.teamcode.lib.bot.DriveModel
import org.futurerobotics.jargon.pathing.trajectory.*
import org.futurerobotics.jargon.profile.MotionProfileGenParams


val motionConstraints = MotionConstraintSet(
    MaxVelConstraint(1.5),
    MaxAngularVelConstraint(1.5),
    MaxTotalAccelConstraint(5.0),
    MaxAngularAccelConstraint(5.0),
    MaxMotorVoltage(DriveModel.driveModel, DriveModel.driveModel, 11.0)
)

val genParams = MotionProfileGenParams(0.0, 0.0, 0.04, 0.04)
