package org.firstinspires.ftc.teamcode.lib.blueyay

import com.qualcomm.robotcore.util.RobotLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.futurerobotics.bluejay.original.detectors.ImageDetector
import org.futurerobotics.bluejay.original.detectors.OpenCvDetector
import org.futurerobotics.bluejay.original.detectors.foundation.Pipeline
import org.futurerobotics.bluejay.original.detectors.foundation.SkyStone
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.syncedLoop

@UseExperimental(ExperimentalCoroutinesApi::class)
class SkystoneDetector(imageDetector: ImageDetector) : TickerSystem {

    private val detector = OpenCvDetector(imageDetector)

    val channel = ConflatedBroadcastChannel<List<SkyStone>>()

    init {
        Pipeline.doFoundations = false
        Pipeline.doStones = false
        Pipeline.doSkyStones = true
    }

    fun cancel() {
        job?.cancel()
    }

    @Volatile
    private var job: Job? = null

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        job = scope.launch {
            detector.start()
            try {

                ticker.listener(0).syncedLoop {
                    val skystones = detector.skystones
                    if (skystones.isNotEmpty()) {
                        channel.send(skystones)
                    }
                    false
                }
            } finally {
                RobotLog.d("skystone stop")
                detector.stop()
            }
        }
    }
}
