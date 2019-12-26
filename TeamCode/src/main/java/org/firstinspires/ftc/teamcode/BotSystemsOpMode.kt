package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.coroutineScope
import org.firstinspires.ftc.teamcode.system.BotSystem
import org.firstinspires.ftc.teamcode.system.CoroutineScopeElement
import org.firstinspires.ftc.teamcode.system.Element
import org.firstinspires.ftc.teamcode.system.OpModeElement
import org.futurerobotics.ftcutils.CoroutineOpMode
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.coroutines.EmptyCoroutineContext

/**
 * An op mode which runs a [BotSystem], given some [initialElements].
 *
 * - [OpModeElement] will be _replaced_
 * - [CoroutineScopeElement] will be overriden (or rather it is reused).
 */
abstract class BotSystemsOpMode(
    private val initialElements: Collection<Element>
) : CoroutineOpMode(
    (initialElements)
        .filterIsInstance<CoroutineScopeElement>()
        .let {
            require(it.size <= 1) { "Cannot have two elements with the same identifier" }
            it.firstOrNull()?.coroutineContext ?: EmptyCoroutineContext
        }
) {

    constructor(vararg initialElements: Element) : this(initialElements.asList())

    protected open fun additionalElements(): List<Element> = emptyList()

    final override suspend fun runOpMode() = coroutineScope {
        val initialElements = (initialElements + additionalElements())
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
