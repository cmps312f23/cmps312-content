package maps.view.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import maps.view.screens.BasicMapScreen
import maps.view.screens.LocationPermissionScreen
import maps.view.screens.MapMarkersClustering
import maps.view.screens.MapTypeScreen

/**
 * It receives navController to navigate between screens
 */
@ExperimentalFoundationApi
@Composable
fun AppNavigator(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = "basic-map",
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)
    ) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
        */
        composable(NavDestination.BasicMap.route) {
            BasicMapScreen()
        }

        composable(NavDestination.MarkersClustering.route) {
            MapMarkersClustering()
        }

        composable(NavDestination.MapType.route) {
            MapTypeScreen()
        }

        composable(NavDestination.LocationPermission.route) {
            LocationPermissionScreen()
        }
    }
}