package qu.lingosnacks.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

@Composable
fun getCurrentScreen(navController: NavHostController): NavDestination? {
    val navBackStackEntry = navController.currentBackStackEntry
    return navBackStackEntry?.destination
}

fun getPreviousScreen(navController: NavHostController): NavDestination? {
    val navBackStackEntry = navController.previousBackStackEntry
    return navBackStackEntry?.destination
}

fun getPreviousScreenRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.previousBackStackEntry
    return navBackStackEntry?.destination?.route
}