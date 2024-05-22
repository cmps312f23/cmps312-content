package coroutines.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coroutines.model.StockQuote
import coroutines.view.removeTrailingComma
import coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.*

class StockQuotesViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    var selectedCompanies by mutableStateOf("Tesla, Apple, Microsoft, IBM,")

    var companyStockQuotes = mutableStateListOf<StockQuote>()
    var runJobsInParallel by mutableStateOf(true)

    var requestState by mutableStateOf(RequestState.SUCCESS)
    var executionDuration by mutableStateOf(0L)
    var errorMessage by mutableStateOf("")

    private suspend fun getStockQuotesSequential(companies: List<String>) {
            companies.forEach {
                try {
                    val quote = stockQuoteService.getStockQuote(it)
                    companyStockQuotes.add(quote)
                } catch (e: Exception) {
                    val msg = e.message ?: "Request failed for $it"
                    errorMessage = errorMessage + msg + "\n"
                    println(">>> Debug: $e")
                }
            }
    }

    private suspend fun getStockQuotesInParallel(companies: List<String>) =
        withContext(Dispatchers.IO) {
            // Running the jobs in a supervisorScope is important so that if a child job fails
            // other child jobs are not cancelled
            supervisorScope {
                companies.map { async { stockQuoteService.getStockQuote(it) } }
                         .map {
                            try {
                                it.await()
                            } catch (e: Exception) {
                                val msg = e.message ?: "Request failed for $it"
                                errorMessage = errorMessage + msg + "\n"
                                println(">>> Debug: $e")
                                StockQuote()
                            }
                         }
            }
        }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        errorMessage = exception.message ?: "Request failed."
        requestState = RequestState.CANCELLED
        println(">>> Debug: $errorMessage")
    }

    fun getStockQuotes() {
        val startTime = System.currentTimeMillis()
        requestState = RequestState.RUNNING
        executionDuration = 0L
        errorMessage = ""
        companyStockQuotes.clear()

        val companiesList = selectedCompanies.trim().removeTrailingComma().split(",")

        val job =
            viewModelScope.launch(exceptionHandler) {
                if (runJobsInParallel) {
                    val quotes = getStockQuotesInParallel(companiesList)
                    companyStockQuotes.addAll(quotes)
                } else {
                    getStockQuotesSequential(companiesList)
                }
            }

        job.invokeOnCompletion {
            if (!job.isCancelled) {
                executionDuration = (System.currentTimeMillis() - startTime) / 1000
                requestState = RequestState.SUCCESS
                println(">>> Debug: Job done. Total execution time: ${executionDuration}s")
            }
        }
    }
}