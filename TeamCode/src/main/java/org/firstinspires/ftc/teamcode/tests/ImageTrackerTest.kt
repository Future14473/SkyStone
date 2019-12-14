package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem

@UseExperimental(ExperimentalCoroutinesApi::class)
@TeleOp
@Disabled
class ImageTrackerTest : BotSystemsOpMode(BotSystems.ImageTracker) {

    override suspend fun RobotSystem.onStart() {
        var i = 0
        while (opModeIsActive()) {
            delay(100)
            val channel = imageTracker.poseChannel.openSubscription()
            telemetry.run {
                addLine("#$i: ")
                addLine("Position: ${imageTracker.mostRecentPose}")
                addLine("Channel: ${channel.poll()}")
                update()
            }
            i++
        }
    }
}
