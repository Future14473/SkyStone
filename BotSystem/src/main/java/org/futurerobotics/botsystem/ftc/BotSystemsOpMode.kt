package org.futurerobotics.botsystem.ftc

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.futurerobotics.botsystem.BotSystem
import org.futurerobotics.botsystem.Element
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * An op mode which runs a [BotSystem]. The elements of the bot system are given in [getElements].
 * [OpModeElement] will be added to the given elements.
 *
 * This will init, start, and stop the [BotSystem] at appropriate times.
 *
 * One can also override the [additionalRun] function to run additional actions, and use like [CoroutineOpMode].
 *
 * The [botSystem] will then be available.
 */
abstract class BotSystemsOpMode(
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : CoroutineOpMode(coroutineContext) {

    private val lateScope = object : CoroutineScope {
        override lateinit var coroutineContext: CoroutineContext
    }

    protected abstract fun getElements(): Array<out Element>

    @Suppress("LeakingThis")
    protected val botSystem by lazy {
        BotSystem.create(
            lateScope,
            (getElements().asSequence() + OpModeElement(this)).asIterable()
        )
    }


    final override suspend fun runOpMode() = coroutineScope {
        lateScope.coroutineContext = coroutineContext
        telemetry.addLine("Initializing...")
        telemetry.update()
        botSystem.init()
        telemetry.addLine("Done initializing")
        telemetry.update()
        launch {
            additionalRun()
        }
        waitForStart()
        botSystem.start()
    }

    /**
     * Launched in a separate coroutine on start. Use to run more stuff if you want for testing.
     */
    protected open suspend fun additionalRun() {
    }
}
