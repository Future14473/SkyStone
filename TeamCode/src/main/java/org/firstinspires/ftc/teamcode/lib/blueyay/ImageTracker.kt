package org.firstinspires.ftc.teamcode.lib.blueyay

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.bluejay.original.detectors.ImageDetector
import org.futurerobotics.jargon.math.Pose2d
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.syncedLoop

@UseExperimental(ExperimentalCoroutinesApi::class)
class ImageTracker(opMode: OpMode) : TickerSystem {

    val detector = ImageDetector(opMode, true)

    val poseChannel = ConflatedBroadcastChannel<Pose2d>()

    val mostRecentPose: Pose2d? get() = poseChannel.valueOrNull

    @Volatile
    private var job: Job? = null

    fun cancel() {
        job?.cancel()
    }

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        job = scope.launch {
            detector.start()
            try {
                ticker.listener(0).syncedLoop {
                    detector.position?.let {
                        poseChannel.send(it.toPose2d())
                    }
                    false
                }
            } finally {
                detector.stop()
            }
        }
    }
}
