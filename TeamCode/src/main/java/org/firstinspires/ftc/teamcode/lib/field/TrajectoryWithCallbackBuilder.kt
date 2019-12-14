package org.firstinspires.ftc.teamcode.lib.field

import kotlinx.coroutines.Job
import org.futurerobotics.jargon.pathing.*
import org.futurerobotics.jargon.pathing.trajectory.Trajectory
import org.futurerobotics.jargon.running.CompletionCallback
import org.futurerobotics.jargon.running.completionCallback

class TrajectoryWithCallbackBuilder(val trajectory: Trajectory) {
    private val callbacks = mutableListOf<TrajectoryCallback>()

    private inline fun addCallback(block: (CompletionCallback) -> TrajectoryCallback): Job = Job().also {
        callbacks.add(block(it.completionCallback()))
    }

    fun addTimeCallback(time: Double) = addCallback {
        TimeCallback(time, it)
    }

    fun addDistanceCallback(distance: Double) = addCallback {
        DistanceCallback(distance, it)
    }

    fun addDoneCallback() = addCallback {
        TrajectoryCompleteCallback(it)
    }

    fun addStartedCallback() = addCallback {
        TrajectoryStartedCallback(it)
    }

    fun build(): TrajectoryWithCallbacks = TrajectoryWithCallbacks(trajectory, callbacks)
}

fun Trajectory.callbackBuilder() =
    TrajectoryWithCallbackBuilder(this)
