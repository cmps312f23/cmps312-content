package coroutines.view

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import view.navigation.AppNavigator
import view.navigation.NavDestination

@Composable
fun MainScreen() {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) {
        paddingValues -> AppNavigator(navController = navController, padding = paddingValues)
    }
}

/**
 * It receives navcontroller to navigate between screens
*/
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)
        val navItems = listOf(
            NavDestination.WhyCoroutines,
            NavDestination.CancelCoroutine,
            NavDestination.StockQuote,
            NavDestination.StockQuotes)

        navItems.forEach { navItem ->
            NavigationBarItem(
                //if currentRoute is equal to the nav item route then set selected to true
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        /* Navigate to the destination only if we’re not already on it,
                        avoiding multiple copies of the destination screen on the back stack */
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = navItem.icon), contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}