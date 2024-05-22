package coroutines.console

import kotlinx.coroutines.*

suspend fun main() {
    val startTime = System.currentTimeMillis()

    // Create a coroutine scope to launch coroutines
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val jobs = List(10_000) {
        coroutineScope.launch {
            delay(5000)
            println(".")
        }
    }
    // Wait for the coroutine jobs to complete
    jobs.forEach { it.join() }
    val executionDuration = System.currentTimeMillis() - startTime
    println("\n>>> Job done. Total elapse time ${executionDuration/1000}s <<<")
}