package org.firstinspires.ftc.teamcode.system

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.debug.DebugProbes
import org.junit.jupiter.api.Test

class BotSystemTest {
    @UseExperimental(ExperimentalCoroutinesApi::class)
    @Test
    fun runIt() {
        DebugProbes.install()
        val system = BotSystem()
        val job = GlobalScope.launch {
            system.initSuspend(
                DependsOn(Dependency1::class.java),
                LoopAFewTimes(),
                Receive()
            )
            system.startSuspend()
            val job = system.get<CoroutineScopeElement>().job
            (job as CompletableJob).complete()
            job.join()
        }
        runBlocking {
            try {
                withTimeout(3000) {
                    job.join()
                }
            } catch (e: TimeoutCancellationException) {
                println("Timed out:")
                DebugProbes.dumpCoroutines()
                job.cancel()
            }
        }
    }
}

private class Dependency1 : AbstractElement(Dependency2::class.java) {
    override fun init(botSystem: BotSystem) {
    }
}

private class Dependency2 : AbstractElement(/*Dependency1::class.java*/) {
    override fun init(botSystem: BotSystem) {
    }
}


private class LoopAFewTimes : LinearElement() {
    override fun moreInit(botSystem: BotSystem) {
    }

    override fun runElement() {
        repeat(5) {
            delay(50)
            println(it)
        }
    }
}

private class Send : CoroutineElement() {
    val channel = Channel<String>()
    override fun moreInit1(botSystem: BotSystem) {
    }

    @UseExperimental(ExperimentalCoroutinesApi::class)
    override suspend fun runElement() = coroutineScope<Unit> {
        repeat(10) {
            channel.send("Hey $it")
            delay(25)
        }
        channel.close()
    }
}

private class Receive : CoroutineElement() {
    private val send: Send by botSystem
    override fun moreInit1(botSystem: BotSystem) {
    }

    override suspend fun runElement() {
        for (s in send.channel) {
            println(s)
        }
    }
}
