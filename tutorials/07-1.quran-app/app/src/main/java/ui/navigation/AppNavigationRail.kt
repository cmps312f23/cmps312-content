package ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import ui.common.getCurrentRoute

@Composable
fun AppNavigationRail(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    //observe current route to change the icon color,label color when navigated
    val currentRoute = getCurrentRoute(navController)
    val navItems = listOf(NavDestination.Surahs, NavDestination.Stats)

    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        //header = header,
        //content = content,
    ) {
        navItems.forEach { navItem ->
            NavigationRailItem(
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