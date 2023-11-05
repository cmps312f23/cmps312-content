package view.navigation

import compose.nav.R

sealed class NavDestination(val route: String, val title: String, val icon: Int) { //ImageVector) {
    object WhyCoroutines : NavDestination(route = "why", title = "Why", icon = R.drawable.ic_why)
    object CancelCoroutine : NavDestination(route = "cancel", title = "Cancel", icon = R.drawable.ic_cancel)
    object StockQuote : NavDestination(route = "quote", title = "Stock Quote", icon = R.drawable.ic_quote)
    object StockQuotes : NavDestination(route = "quotes", title = "Stock Quotes", icon = R.drawable.ic_quotes)
}