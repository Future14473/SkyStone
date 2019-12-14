package org.firstinspires.ftc.teamcode.lib.system

import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.math.distTo

enum class AutoArmState {
    Ready {
        override suspend fun AutoArmRunner.run(): AutoArmState {
            claw.open()
            moveArmToAndWait(ARM_READY_PLUS)
            moveArmToAndWait(ARM_READY)
            waitUntil {
                actualLiftHeight distTo 0.0 <= LIFT_DOWN_TOLERANCE
            }
            return stateChannel.receive()
        }
    },
    Grab {
        override suspend fun AutoArmRunner.run(): AutoArmState {
            moveArmToAndWait(ARM_GRAB)
            claw.close()
            return stateChannel.receive()
        }
    },
    Up {
        override suspend fun AutoArmRunner.run(): AutoArmState {
            moveArmToAndWait(ARM_UP)
            return stateChannel.receive()
        }
    },
    Drop {
        override suspend fun AutoArmRunner.run(): AutoArmState {
            moveArmToAndWait(ARM_FORWARD + 20 * deg)
            claw.open()
            return Ready
        }
    }
    ;

    abstract suspend fun AutoArmRunner.run(): AutoArmState
}

