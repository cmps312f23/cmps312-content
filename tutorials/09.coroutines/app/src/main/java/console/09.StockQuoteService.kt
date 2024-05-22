package coroutines.console

import android.os.Build
import androidx.annotation.RequiresApi
import coroutines.webapi.SimulatedStockQuoteService
import coroutines.webapi.StockQuoteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
suspend fun main() {
    val startTime = System.currentTimeMillis()
    val job = CoroutineScope(Dispatchers.IO).launch {
        val company = "Apple"

        val stockQuoteService = SimulatedStockQuoteService()
        val symbol = stockQuoteService.getStockSymbol(company)

        val quote = StockQuoteService.getStockQuote(symbol)
        println(">> $company (${quote.symbol}) = ${quote.price}")
        println(">> MarketStockQuote: $quote")
    }

    job.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total execution time: ${executionDuration/1000}s <<<")
    }

    // Wait for the job to finish otherwise main will exit
    job.join()
}