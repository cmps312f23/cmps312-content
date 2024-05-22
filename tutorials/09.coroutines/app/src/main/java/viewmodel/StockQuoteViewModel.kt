package coroutines.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coroutines.model.StockQuote
import coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.launch

enum class RequestState {
    RUNNING,
    SUCCESS,
    CANCELLED
}

class StockQuoteViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    var companyList = mutableStateListOf<String>()
    var selectedCompany by mutableStateOf("")

    var requestState by mutableStateOf(RequestState.SUCCESS)
    var stockQuote by mutableStateOf(StockQuote())
    var errorMessage by mutableStateOf("")

    // Auto initialize the companies list
    init {
        viewModelScope.launch {
            getCompanies()
        }
    }

    fun getStockQuote() {
       requestState = RequestState.RUNNING
        viewModelScope.launch {
            try {
                stockQuote = stockQuoteService.getStockQuote(selectedCompany)
                requestState = RequestState.SUCCESS
            } catch (e: Exception) {
                requestState = RequestState.CANCELLED
                errorMessage = e.message ?: "Request failed"
            }
        }
    }

    private suspend fun getCompanies() {
        companyList.clear()
        companyList.addAll(
            stockQuoteService.getCompanies()
        )
    }
}