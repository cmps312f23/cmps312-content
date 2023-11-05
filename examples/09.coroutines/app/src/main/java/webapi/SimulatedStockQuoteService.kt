package coroutines.webapi

import coroutines.model.StockQuote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SimulatedStockQuoteService {
    private val companies = mapOf(
        "Apple" to "AAPL",
        "Amazon" to "AMZN",
        "Alibaba" to "BABA",
        "Salesforce" to "CRM",
        "Facebook" to "FB",
        "Google" to "GOOGL",
        "IBM" to "IBM",
        "Johnson & Johnson" to "JNJ",
        "Microsoft" to "MSFT",
        "Tesla" to "TSLA"
    )

    suspend fun getStockSymbol(name: String): String {
        println("getStockSymbol($name) started...")
        //Suspend for 1500ms to simulate fetching stock symbol from a Web API
        delay(1500)
        val symbol = companies[name.trim()]
        println("~~getStockSymbol($name) result: $symbol")
        return symbol!!
    }

    suspend fun getPrice(symbol: String): Int {
        println("getPrice($symbol) started...")
        //Suspend for 1000ms to simulate fetching stock price from a Web API
        delay(1000)
        val price = (50..500).random()
        println("~~getPrice($symbol) result: $price")
        return price
    }

    suspend fun getStockQuote(name: String) = withContext(Dispatchers.IO) {
        if (!companies.containsKey(name.trim())) throw Exception("Getting stock quote failed. '$name' does not exit.")
        val symbol = getStockSymbol(name)
        val price = getPrice(symbol)
        StockQuote(name.trim(), symbol, price)
    }

    suspend fun getCompanies(): List<String> {
        println("getCompanies started...")
        //Suspend for 400ms to simulate fetching companies from a Web API
        delay(400)
        return companies.keys.toList()
    }
}