package coroutines.console

import kotlinx.coroutines.*

suspend fun sum(n: Int): Int {
    delay(n.toLong())
    println("Done sum 0..$n")
    return (0 until n).sum()
}

suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        val first = async { sum(20) }
        val second = async { sum(10) }

        println("Waiting...")
        println(">> First - sum(0..20) = ${first.await()}")
        println(">> Second - sum(0..10) = ${second.await()}")
    }

    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}