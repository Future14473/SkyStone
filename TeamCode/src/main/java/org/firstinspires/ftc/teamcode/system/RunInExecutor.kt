package org.firstinspires.ftc.teamcode.system

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService

private class WrappedException(e: Throwable) : Throwable(e)

/**
 * Switches from suspend world to blocking world. Runs the given [callable] in the the given executor,
 * and suspends current coroutine until it is done. Rethrows exceptions if they exist.
 *
 * This may throw [CancellationException] when this is cancelled AND the callable completable normally.
 */
suspend fun <T> runAndWaitInExecutor(executor: ExecutorService, callable: () -> T): T {
    val deferred = CompletableDeferred<T>()
    val future = executor.submit {
        try {
            deferred.complete(callable())
        } catch (e: Throwable) {
            deferred.completeExceptionally(WrappedException(e))
        }
    }
    try {
        try {
            // Can only throw CancellationException or WrappedException.
            // If cancelled, then it is cancelled.
            // If wrapped, will unwrap will unwrap it.
            return deferred.await()
        } catch (e: CancellationException) { // it is cancelled.
            future.cancel(true)
            withContext(NonCancellable) {
                // We wait again, possibly throwing more on cancellation, or else ignore result
                // and rethrow cancellation.
                deferred.await()
            }
            throw e
        }
    } catch (e: WrappedException) {
        throw e.cause!!
    }
}

