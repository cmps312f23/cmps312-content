package ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import ui.screens.SearchScreen
import ui.screens.SettingsScreen
import ui.user.UserDetailsScreen
import ui.user.UserViewModel

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
    val userViewModel = viewModel<UserViewModel>()
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = NavDestination.Users.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app */

        composable(NavDestination.Users.route) {
            UsersScreen(userViewModel, onNavigateToDetails = { userId ->
                navController.navigate( "${NavDestination.UserDetails.route}/$userId")
            })
        }

        composable("${NavDestination.UserDetails.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("userId")?.let { userId ->
                val user = userViewModel.getUser(userId)
                UserDetailsScreen(user,
                    onNavigateBack = { navController.navigate(NavDestination.Users.route) })
            }
        }

        composable(NavDestination.Search.route) {
            SearchScreen()
        }

        composable(NavDestination.Apps.route) {
            // Example screen that demonstrates how to start activities from other apps
            ExternalAppScreen()
        }

        composable(NavDestination.Profile.route) {
            ProfileScreen()
        }

        composable(NavDestination.Addresses.route) {
            AddressesScreen()
        }

        composable(NavDestination.Orders.route) {
            OrdersScreen()
        }

        composable(NavDestination.Settings.route) {
            SettingsScreen()
        }

        composable(NavDestination.FAQ.route) {
            FAQScreen()
        }
    }
}