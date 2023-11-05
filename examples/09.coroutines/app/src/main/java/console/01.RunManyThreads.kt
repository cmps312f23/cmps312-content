package coroutines.console

import kotlin.concurrent.thread

fun main() {
    val startTime = System.currentTimeMillis()
    val jobs = List(10_000) {
        thread {
            Thread.sleep(5000)
            println(".")
        }
    }
    // Wait for each job to finish
    jobs.forEach { it.join() }
    val executionDuration = System.currentTimeMillis() - startTime
    println("\n>>> Job done. Total elapse time ${executionDuration/1000}s <<<")
}