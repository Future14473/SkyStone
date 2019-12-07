package org.firstinspires.ftc.teamcode.lib.system

import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.mechanics.MotorModel
import org.futurerobotics.jargon.mechanics.NominalDriveModel
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
const val botMass = 24 * lbs
val driveModel = NominalDriveModel.mecanumLike(
    botMass,
    botMass / 3 * (16 * `in`).pow(2), //todo: find out empirically
    wheelTransmission,
    2 * `in` / sqrt(2.0),
    8 * `in`,
    7 * `in`
)
