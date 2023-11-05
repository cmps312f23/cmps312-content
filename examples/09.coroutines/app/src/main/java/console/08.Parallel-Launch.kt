package coroutines.console

import kotlinx.coroutines.*

suspend fun main() {
    println("Sequential Execution:")
    delay(1200)
    println("Hello")

    delay(600)
    println("Big")

    delay(300)
    println("Beautiful")

    println("\nParallel Execution:")
    val job = CoroutineScope(Dispatchers.Default).launch {
        launch {
            delay(1200)
            println("Hello")
        }
        launch {
            delay(600)
            println("Big")
        }
        launch {
            delay(300)
            println("Beautiful")
        }
    }

    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}