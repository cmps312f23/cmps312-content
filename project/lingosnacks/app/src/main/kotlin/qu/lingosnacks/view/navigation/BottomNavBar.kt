package qu.lingosnacks.view.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import qu.lingosnacks.utils.getCurrentRoute

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)

        val navItems = listOf(NavDestination.Packages, NavDestination.Score)

        //Bottom nav items we declared
        navItems.forEach { navItem ->
            NavigationBarItem(
                //it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    // For each screen either an icon or vector resource is provided
                    val icon = navItem.icon //?: ImageVector.vectorResource(navItem.iconResourceId!!)
                    Icon(imageVector = icon, contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}