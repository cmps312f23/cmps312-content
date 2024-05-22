package view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coroutines.view.screens.CancelCoroutineScreen
import coroutines.view.screens.StockQuotesScreen
import coroutines.view.screens.StockQuoteScreen
import coroutines.view.screens.WhyCoroutinesScreen

/**
 * It receives navController to navigate between screens,
 * padding values -> Since BottomNavigation has some heights,
 * to avoid clipping of screen, we set padding provided by scaffold
 */
@Composable
fun AppNavigator(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = NavDestination.WhyCoroutines.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
        */
        composable(NavDestination.WhyCoroutines.route) {
            WhyCoroutinesScreen()
        }

        composable(NavDestination.CancelCoroutine.route) {
            CancelCoroutineScreen()
        }

        composable(NavDestination.StockQuote.route) {
            StockQuoteScreen()
        }

        composable(NavDestination.StockQuotes.route) {
            StockQuotesScreen()
        }
    }
}