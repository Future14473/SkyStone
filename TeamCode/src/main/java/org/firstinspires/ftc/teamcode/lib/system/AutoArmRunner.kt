package org.firstinspires.ftc.teamcode.lib.system

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive

class AutoArmRunner(system: RobotSystem) : ArmStateScope(system) {
    private var state: AutoArmState = AutoArmState.Ready

    val stateChannel = Channel<AutoArmState>(Channel.UNLIMITED)
    override suspend fun update() {
    }

    override suspend fun CoroutineScope.runSystem() {
        while (isActive) {
            state = with(state) { run() }
        }
    }
}
