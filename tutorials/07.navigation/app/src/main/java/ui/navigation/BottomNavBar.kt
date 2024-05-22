package ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import ui.screens.getCurrentRoute

/**
 * It receives navcontroller to navigate between screens,
*/
@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)

        val navItems = listOf(NavDestination.Users, NavDestination.Search, NavDestination.Apps)

        //Bottom nav items we declared
        navItems.forEach { navItem ->
            NavigationBarItem(
                //it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    // For each screen either an icon or vector resource is provided
                    val icon = navItem.icon ?: ImageVector.vectorResource(navItem.iconResourceId!!)
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