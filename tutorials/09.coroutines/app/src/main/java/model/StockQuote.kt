package coroutines.model

data class StockQuote(val name: String = "", val symbol: String = "", val price: Int = 0) {
    override fun toString(): String {
        return if (name.isNotEmpty())
            "$name ($symbol) = $price"
        else
            ""
    }
}