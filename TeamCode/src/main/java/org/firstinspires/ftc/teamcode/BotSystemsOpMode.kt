package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.coroutineScope
import org.firstinspires.ftc.teamcode.system.BotSystem
import org.firstinspires.ftc.teamcode.system.CoroutineScopeElement
import org.firstinspires.ftc.teamcode.system.Element
import org.firstinspires.ftc.teamcode.system.OpModeElement
import org.futurerobotics.jargon.ftcbridge.CoroutineOpMode
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * An op mode which runs a [BotSystem], given some [initialElements].
 *
 * - [OpModeElement] will be _replaced_
 * - [CoroutineScopeElement] will be overriden (or rather it is reused).
 */
abstract class BotSystemsOpMode @JvmOverloads constructor(
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : CoroutineOpMode(coroutineContext) {

    protected abstract fun getElements(): List<Element>

    final override suspend fun runOpMode() = coroutineScope {
        val initialElements = getElements()
            .groupBy { it.identifierClass }
            .entries.associateTo(HashMap()) { (cls, list) ->
            if (cls != null)
                require(list.size == 1) { "Cannot have two elements with the same identifier" }
            val element = list.first()
            (cls ?: element) to element
        }

        fun replaceElement(element: Element) {
            initialElements[element.identifierClass!!] = element
        }
        replaceElement(OpModeElement(this@BotSystemsOpMode))
        replaceElement(CoroutineScopeElement(this))
        val system = BotSystem()
        system.initSuspend(initialElements.values)
        waitForStart()
        system.startSuspend()
    }
}
