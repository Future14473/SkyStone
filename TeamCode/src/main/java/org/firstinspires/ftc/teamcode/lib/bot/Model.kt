package org.firstinspires.ftc.teamcode.lib.bot

import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.mechanics.FixedWheelDriveModel
import org.futurerobotics.jargon.mechanics.MotorModel
import org.futurerobotics.jargon.mechanics.TransmissionModel
import kotlin.math.pow
import kotlin.math.sqrt

//models the bot. Used in [Drive]


val wheelMotorModel = MotorModel.fromMotorData(
    12 * volts,
    260 * ozf * `in`,
    9.2 * A,
    435 * rev / mins,
    0.25 * A
)
val wheelTransmission = TransmissionModel.fromTorqueMultiplier(wheelMotorModel, 2.0, 0.0, 0.9)
const val botMass = 33.4 * lbs
val moi = botMass / 3 * (16 * `in`).pow(2) //todo: find out empirically
val driveModel = FixedWheelDriveModel.mecanumLike(
    botMass,
    moi,
    wheelTransmission,
    2 * `in` / sqrt(2.0),
    8 * `in`,
    7 * `in`
)
