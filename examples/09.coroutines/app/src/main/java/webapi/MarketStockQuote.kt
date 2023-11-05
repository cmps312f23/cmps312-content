package coroutines.webapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketStockQuote (
    val status: String? = null,
    @SerialName("from") val date: String? = null,
    val symbol: String? = null,
    @SerialName("open") val price: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val close: Double? = null,
)

// Daily Open / Close
// https://api.polygon.io/v1/open-close/IBM/2020-10-13?apiKey=Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp

//suspend fun PolygonStocksClient.getDailyOpenClose(symbol: String, date: String): DailyOpenCloseDTO =
//polygonClient.fetchResult { path("v1", "open-close", symbol, date) }

/*
{
    "status": "OK",
    "from": "2020-10-19",
    "symbol": "AAPL",
    "open": 119.96,
    "high": 120.419,
    "low": 115.66,
    "close": 115.98,
    "volume": 115140370,
    "afterHours": 117.4,
    "preMarket": 120.1
}
 */