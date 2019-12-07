package org.firstinspires.ftc.teamcode.lib

import kotlinx.coroutines.coroutineScope
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode

/**
 * An op mode which only runs [BotSystems].
 */
abstract class BotSystemsOnly(private vararg val systems: BotSystems) : CoroutineOpMode() {

    private lateinit var system: RobotSystem
    final override suspend fun runOpMode() = coroutineScope {
        system = RobotSystem(this@BotSystemsOnly, *systems)
        waitForStart()
        system.start(this)
    }
}
