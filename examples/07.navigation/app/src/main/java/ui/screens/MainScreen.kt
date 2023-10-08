package ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.nav.AppNavigator
import ui.components.nav.BottomNavBar
import ui.components.nav.NavDrawer

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
    //create a scaffold state
    //val scaffoldState = rememberScaffoldState()

    //Create a coroutine scope. Opening of Drawer and snackbar should happen in background thread without blocking main thread
    val coroutineScope = rememberCoroutineScope()

    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavDrawer(navController, drawerState) {
        Scaffold(
            //pass the scaffold state
            //scaffoldState = scaffoldState,
            topBar = {
                val currentRoute = getCurrentRoute(navController) ?: ""
                displayMessage(LocalContext.current, currentRoute)
                // Hide the TopBar for the User Details Screen & Verses Screen
                if (!currentRoute.startsWith(Screen.UserDetails.route)) {
                    TopBar(coroutineScope, drawerState) //, scaffoldState)
                }
            },
            bottomBar = { BottomNavBar(navController) },
            //drawerContent = { NavDrawer(navController, coroutineScope, scaffoldState) },
        ) {
                paddingValues ->
                    AppNavigator(navController = navController, padding = paddingValues)
        }
    }
}

//A function which will receive a callback to trigger to opening the drawer
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(coroutineScope: CoroutineScope, drawerState: DrawerState) { //, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = {
            Text(text = "Navigation")
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}