package qu.lingosnacks.view

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import qu.lingosnacks.view.navigation.AppNavigator
import qu.lingosnacks.view.navigation.BottomNavBar
import qu.lingosnacks.view.navigation.NavDestination
import qu.lingosnacks.view.navigation.TopNavBar
import qu.lingosnacks.utils.getCurrentRoute
import qu.lingosnacks.viewmodel.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    /*val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //Screens that don't have a bottom navigation bar
    val hideNavBarNavDestinations = listOf(
        NavDestination.Login,
        NavDestination.Signup
    )*/

    val authViewModel = viewModel<AuthViewModel>()
    Scaffold(
        topBar = {
            //if (hideNavBarNavDestinations.none { it.route == currentRoute })
                TopNavBar(navController, authViewModel)
        },
        bottomBar = {
            val currentRoute = getCurrentRoute(navController) ?: ""
            // Hide the TopBar for the User Details & Verses Screens
            if (!currentRoute.startsWith(NavDestination.Login.route)) {
                BottomNavBar(navController)
            }
        },
        modifier = modifier
    ) {
        AppNavigator(authViewModel, navController, paddingValues = it)
    }
}

