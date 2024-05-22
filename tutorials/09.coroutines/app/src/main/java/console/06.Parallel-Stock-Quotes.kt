package coroutines.console

import coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

suspend fun main() {
    val startTime = System.currentTimeMillis()
    val job = CoroutineScope(Dispatchers.IO).launch {
        val stockQuoteService = SimulatedStockQuoteService()
        val deferred = async { stockQuoteService.getStockQuote("Apple") }
        val deferred2 = async { stockQuoteService.getStockQuote("Tesla") }
        val deferred3 = async { stockQuoteService.getStockQuote("Google") }

        // I may cancel this one for example
        //deferred3.cancel()

        val quote = deferred.await()
        println(">> $quote")

        val quote2 = deferred2.await()
        println(">> $quote2")

        if (deferred3.isActive) {
            val quote3 = deferred3.await()
            println(">> $quote3")
        }
    }

    // I may cancel the whole job
    //job.cancel()

    job.invokeOnCompletion {
        if (job.isCancelled) {
            println("Job cancelled")
        } else {
            val executionDuration = System.currentTimeMillis() - startTime
            println(">>> Job done. Total execution time: ${executionDuration / 1000}s <<<")
        }
    }
    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}
