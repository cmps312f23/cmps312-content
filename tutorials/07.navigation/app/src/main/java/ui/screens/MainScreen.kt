package ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ui.navigation.AppNavigator
import ui.navigation.BottomNavBar
import ui.navigation.NavDestination
import ui.navigation.NavDrawer
import ui.navigation.TopAppBar
import ui.theme.AppTheme

fun displayMessage(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun MainScreen() {
    //Create a coroutine scope. Opening of Drawer and snackbar should happen in background thread without blocking main thread
    val coroutineScope = rememberCoroutineScope()

    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavDrawer(navController, drawerState) {
        Scaffold(
            topBar = {
                val currentRoute = getCurrentRoute(navController) ?: ""
                displayMessage(LocalContext.current, currentRoute)
                // Hide the TopBar for the User Details & Verses Screens
                if (!currentRoute.startsWith(NavDestination.UserDetails.route)) {
                    TopAppBar(coroutineScope, drawerState) //, scaffoldState)
                }
            },
            bottomBar = { BottomNavBar(navController) },
        ) {
                paddingValues ->
                    AppNavigator(navController = navController, padding = paddingValues)
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}