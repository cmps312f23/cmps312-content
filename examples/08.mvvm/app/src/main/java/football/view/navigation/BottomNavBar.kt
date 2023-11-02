package football.view.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import football.view.common.getCurrentRoute

/**
 * It receives navcontroller to navigate between screens
*/
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)
        val navItems = listOf(NavDestination.Score, NavDestination.Users, NavDestination.Weather)

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
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}