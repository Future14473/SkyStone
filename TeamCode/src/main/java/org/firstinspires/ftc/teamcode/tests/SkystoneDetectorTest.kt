package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem

@TeleOp
@UseExperimental(ExperimentalCoroutinesApi::class)
class SkystoneDetectorTest : BotSystemsOpMode(BotSystems.SkystoneDetector) {

    override suspend fun RobotSystem.onStart() {
        val channel = skystoneDetector.channel.openSubscription()
        for (i in channel) {
            telemetry.addLine("Received $i")
            telemetry.update()
        }
    }
}
