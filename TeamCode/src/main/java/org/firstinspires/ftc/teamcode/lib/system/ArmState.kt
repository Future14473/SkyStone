package org.firstinspires.ftc.teamcode.lib.system

import org.firstinspires.ftc.teamcode.lib.SYSTEM_PERIOD
import org.firstinspires.ftc.teamcode.lib.bot.Arm
import org.firstinspires.ftc.teamcode.lib.bot.Lift
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.math.distTo
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

//random constants for the arm.

private const val ARM_READY = -80 * deg
private const val ARM_GRAB = -120 * deg
private const val ARM_UP = -20 * deg
private const val ARM_FORWARD = 90 * deg
private val ARM_MAX = Arm.range.endInclusive

private const val LIFT_SENSITIVITY = 0.3 * m * SYSTEM_PERIOD
private const val ARM_SENSITIVITY = 2.5 * rad * SYSTEM_PERIOD
private const val X_SENSITIVITY = 0.4 * m * SYSTEM_PERIOD

private const val LIFT_DOWN_TOLERANCE = 3 * cm
//TODO: actually measure the arm
private const val ARM_LENGTH = 35 * cm

private val X_BOUNDS = ARM_LENGTH * sin(ARM_UP)..ARM_LENGTH
private val Y_BOUNDS = Lift.bounds


/**
 * Arm coroutine state machine.
 */
enum class ArmState {

    Ready {
        override suspend fun TeleOp1.run(): ArmState {
            targetLiftHeight = 0.0
            claw.open()
            moveArmToAndWait(ARM_READY)
            waitUntil {
                actualLiftHeight distTo 0.0 <= LIFT_DOWN_TOLERANCE
            }
            loop {
                if (grabSignal) {
                    moveArmToAndWait(ARM_GRAB)
                    claw.close()
                    return ArmIn
                }
            }
        }
    },
    ArmIn { //claw on robot side, lift down.
        override suspend fun TeleOp1.run(): ArmState {
            targetLiftHeight = 0.0

            loop {
                //if trying to raise or go forward, move.
                val moveSignal = liftSignal + armSignal
                additionalIntakePower = if (moveSignal > 0 || armAngle < ARM_READY) 0.5 else 0.0
                armAngle += moveSignal * ARM_SENSITIVITY
                if (releaseSignal) {
                    additionalIntakePower = 0.0
                    moveArmToAndWait(ARM_GRAB)
                    claw.open()
                    pause(50)
                    return Ready
                }
                if (armAngle >= ARM_UP && moveSignal > 0) {
                    additionalIntakePower = 0.0
                    return OldSchoolControl
                }
            }
        }
    },
    OldSchoolControl { //Arm and lift in full control. Arm can only be in up<->forward positions.
        override suspend fun TeleOp1.run(): ArmState {
            loop {
                val isDown = targetLiftHeight == 0.0 && liftSignal <= 0
                val armMax = if (isDown || armAngle >= ARM_FORWARD) ARM_MAX else ARM_FORWARD

                targetLiftHeight += liftSignal * LIFT_SENSITIVITY
                armAngle = (armAngle + armSignal * ARM_SENSITIVITY).coerceIn(
                    ARM_UP, armMax
                )

                if (isDown && armSignal < 0 && armAngle < 0) return ArmIn
                if (releaseSignal) return Drop
                if (toFancySignal) return FancyControl
            }
        }
    },
    FancyControl {
        override suspend fun TeleOp1.run(): ArmState {
            val l = ARM_LENGTH

            val h0 = targetLiftHeight
            val th0 = armAngle
            var x = l * sin(th0)
            var y = l * cos(th0) + h0
            loop {
                y = (y + liftSignal * LIFT_SENSITIVITY).coerceIn(Y_BOUNDS)
                x = (x + armSignal * X_SENSITIVITY).coerceIn(X_BOUNDS)
                armAngle = asin(x / l)
                targetLiftHeight = y - sqrt(l * l - x * x)
                if (releaseSignal) return Drop
                if (toOldSchoolSignal) return OldSchoolControl
            }
        }
    },
    Drop {
        override suspend fun TeleOp1.run(): ArmState {
            claw.open()
            pause(200)
            if (armAngle >= ARM_FORWARD) {
                targetLiftHeight += 3 * `in`
                pause(200)
            }
            moveArmToAndWait(ARM_UP)
            return Ready
        }
    };

    abstract suspend fun TeleOp1.run(): ArmState
}
