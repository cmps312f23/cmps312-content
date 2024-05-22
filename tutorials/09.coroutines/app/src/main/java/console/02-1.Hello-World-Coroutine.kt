package coroutines.console

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        delay(1000L)
        println("World ! ")
    }
    println("Hello")
    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}