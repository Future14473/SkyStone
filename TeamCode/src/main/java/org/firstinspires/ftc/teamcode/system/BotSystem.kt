package org.firstinspires.ftc.teamcode.system

import kotlinx.coroutines.*
import org.firstinspires.ftc.teamcode.illArg
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * A thing which contains [Element]s.
 *
 * This by itself isn't much but a container, it is the [Element]s that do all the work
 */
open class BotSystem {

    private val mappedElements = HashMap<Class<out Element>, Element>()
    /**
     * Get an [Element] via its [identifier class][Element.identifierClass], or throws an exception if
     * it does not exist.
     *
     * @see [elements]
     */
    fun <S : Element> get(identifier: Class<S>): S =
        tryGet(identifier) ?: error("Element with identifier $identifier does not exist.")

    /**
     * Attempts to get an [Element] via its [identifier class][Element.identifierClass], or `null`
     * it does not exist.
     *
     * @see [elements]
     */
    @Suppress("UNCHECKED_CAST")
    fun <S : Element> tryGet(identifier: Class<S>): S? = mappedElements[identifier] as S?

    private val _elements = ArrayList<Element>()
    /** A collection of all elements. */
    val elements: Collection<Element> = Collections.unmodifiableCollection(_elements)

    private var loaded = false

    /**
     * Initializes with the given [initialElements].
     *
     * If there is a [CoroutineScopeElement] that is used to run using coroutines.
     */
    @Throws(InterruptedException::class)
    fun init(initialElements: Collection<Element>) {
        loadElements(initialElements)
        runBlocking(getCoroutineContext()) {
            doInit()
        }
    }

    /**
     * Initializes with the given [initialElements].
     *
     * If there is a [CoroutineScopeElement] that is used to run using coroutines.
     */
    @Throws(InterruptedException::class)
    fun init(vararg initialElements: Element): Unit = init(initialElements.asList())

    /**
     * Initializes with the given [initialElements].
     *
     * Initialization is done with coroutines.
     */
    suspend fun initSuspend(initialElements: Collection<Element>) {
        loadElements(initialElements)
        doInit()
    }

    /**
     * Initializes with the given [initialElements].
     *
     * Initialization is done with coroutines.
     */
    suspend fun initSuspend(vararg initialElements: Element): Unit = initSuspend(initialElements.asList())

    private suspend fun doInit() = coroutineScope {
        val clsJobs = ConcurrentHashMap<Class<out Element>, Job>()
        val allJobs = elements.map { el ->
            val cls = el.identifierClass
            val job = launch(start = CoroutineStart.LAZY) {
                el.dependsOn.map { clsJobs[it]!! }.joinAll()
                el.initSuspend(this@BotSystem)
            }
            if (cls != null) {
                clsJobs[cls] = job
            }
            job
        }
        allJobs.forEach {
            it.start()
        }
    }


    private fun loadElements(initialElements: Collection<Element>) {
        check(!loaded) { "Elements already loaded" }
        loaded = true

        _elements.ensureCapacity(initialElements.size)

        val elementsToAdd = mutableMapOf<Any, Element>()
        initialElements.forEach {
            val identifierClass = it.identifierClass
            if (identifierClass == null) {
                elementsToAdd[it] = it
            } else {
                if (identifierClass in elementsToAdd) illArg("Cannot have two elements with the same identifierClass")
                elementsToAdd[identifierClass] = it
            }
        }
        val considering = hashSetOf<Class<out Element>>()

        fun addElement(element: Element) {
            val clazz = element.identifierClass
            if (clazz != null) {
                require(clazz.isInstance(element)) { "Element identifier is not a super class of itself" }
                require(clazz !in considering) { "Circular dependency at $clazz" }
                considering += clazz
            }
            element.dependsOn.forEach {
                if (it in mappedElements || it in elementsToAdd) return@forEach
                val defaultElement = Element.tryCreateDefault(it)
                    ?: throw IllegalStateException("Dependency $it does not exit nor have a default creator")
                addElement(defaultElement)
            }
            if (clazz != null) {
                require(clazz.isInstance(element)) { "Element identifier is not a super class of itself" }
                considering -= clazz
                mappedElements[clazz] = element
            }
            _elements += element
        }
        while (elementsToAdd.isNotEmpty()) {
            addElement(elementsToAdd.values.removeFirst())
        }
    }

    /**
     * Starts all [StartableElement]s.
     *
     * If there is a [CoroutineScopeElement] that is used to run using coroutines.
     */
    @Throws(InterruptedException::class)
    fun start() {
        runBlocking(getCoroutineContext()) {
            startSuspend()
        }
    }

    /**
     * Starts all [StartableElement].
     *
     * Each start is a launched coroutine.
     */
    suspend fun startSuspend(): Unit = coroutineScope {
        elements.forEach {
            if (it is StartableElement)
                launch {
                    it.onStart(this@BotSystem)
                }
        }
    }


    private fun getCoroutineContext(): CoroutineContext =
        tryGet<CoroutineScopeElement>()?.coroutineContext ?: EmptyCoroutineContext
}

private fun <T> MutableIterable<T>.removeFirst(): T = iterator().run { next().also { remove() } } //ha! one line!

inline fun <reified T : Element> BotSystem.get() = get(T::class.java)
inline fun <reified T : Element> BotSystem.tryGet() = tryGet(T::class.java)
