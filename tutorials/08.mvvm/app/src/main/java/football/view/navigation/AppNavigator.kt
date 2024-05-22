package football.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import football.view.ScoreScreen
import football.view.UserScreen
import weather.view.WeatherScreen
import football.viewmodel.ScoreViewModel
import football.viewmodel.UserViewModel
import weather.viewmodel.WeatherViewModel

/**
 * It receives navcontroller to navigate between screens,
 * padding values -> Since BottomNavigation has some heights,
 * to avoid clipping of screen, we set padding provided by scaffold
 */
@Composable
fun AppNavigator(
    navController: NavHostController,
    userViewModel: UserViewModel,
    paddingValues: PaddingValues
) {
    val scoreViewModel = viewModel<ScoreViewModel>()
    val weatherViewModel = viewModel<WeatherViewModel>()
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = NavDestination.Score.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
           Route = String -> Screen
        */

        composable(NavDestination.Score.route) {
            ScoreScreen(scoreViewModel)
        }

        composable(NavDestination.Users.route) {
            UserScreen(userViewModel)
        }

        composable(NavDestination.Weather.route) {
            WeatherScreen(weatherViewModel)
        }
    }
}