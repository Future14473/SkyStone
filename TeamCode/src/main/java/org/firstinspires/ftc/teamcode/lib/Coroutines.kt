package org.firstinspires.ftc.teamcode.lib

import kotlinx.coroutines.CoroutineScope
import org.futurerobotics.jargon.running.Ticker
import kotlin.coroutines.coroutineContext

suspend fun getScope() = CoroutineScope(coroutineContext)

suspend inline fun <T> withScope(block: CoroutineScope.() -> T) = getScope().block()

/**
 * A system that runs with a [ticker].
 */
interface TickerSystem {

    fun launchSystem(scope: CoroutineScope, ticker: Ticker)
}
