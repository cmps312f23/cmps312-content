package ui.components.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui.UsersScreen
import ui.screens.AddressesScreen
import ui.screens.ExternalAppScreen
import ui.screens.FAQScreen
import ui.screens.OrdersScreen
import ui.screens.ProfileScreen
import ui.screens.Screen
import ui.screens.SearchScreen
import ui.screens.SettingsScreen
import ui.user.UserDetailsScreen

/**
 * It receives navcontroller to navigate between screens,
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
        startDestination = Screen.Users.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app */

        composable(Screen.Users.route) {
            UsersScreen(onNavigateToDetails = { userId ->
                navController.navigate( "${Screen.UserDetails.route}/$userId")
            })
        }

        composable("${Screen.UserDetails.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("userId")?.let { userId ->
                UserDetailsScreen(userId = userId,
                    onNavigateBack = { navController.navigate(Screen.Users.route) })
            }
        }

        composable(Screen.Search.route) {
            SearchScreen()
        }

        composable(Screen.Apps.route) {
            // Example screen that demonstrates how to start activities from other apps
            ExternalAppScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.Addresses.route) {
            AddressesScreen()
        }

        composable(Screen.Orders.route) {
            OrdersScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }

        composable(Screen.FAQ.route) {
            FAQScreen()
        }
    }
}