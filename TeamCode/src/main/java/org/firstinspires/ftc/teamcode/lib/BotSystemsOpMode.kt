package org.firstinspires.ftc.teamcode.lib

import kotlinx.coroutines.coroutineScope
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.firstinspires.ftc.teamcode.lib.system.RobotSystemImpl
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode

/**
 * An op mode which runs [BotSystems].
 */
abstract class BotSystemsOpMode(private vararg val systems: BotSystems) : CoroutineOpMode() {

    protected lateinit var system: RobotSystem
        private set

    final override suspend fun runOpMode() = coroutineScope {
        val system = RobotSystemImpl(this@BotSystemsOpMode, *systems)
        this@BotSystemsOpMode.system = system
        system.hardware.enableCharging()
        system.onInit()
        waitForStart()
        system.start(this)
        system.onStart()
    }

    protected open suspend fun RobotSystem.onInit() {}
    protected open suspend fun RobotSystem.onStart() {}
}
