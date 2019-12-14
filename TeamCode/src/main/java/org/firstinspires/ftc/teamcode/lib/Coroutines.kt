package org.firstinspires.ftc.teamcode.lib

import kotlinx.coroutines.CoroutineScope
import org.futurerobotics.jargon.running.Ticker
import kotlin.coroutines.coroutineContext

suspend fun recoverScope() = CoroutineScope(coroutineContext)

suspend inline fun <T> withScope(block: CoroutineScope.() -> T) = recoverScope().block()

/**
 * A system that runs with a [ticker].
 */
interface TickerSystem {

    fun launchSystem(scope: CoroutineScope, ticker: Ticker)
}
