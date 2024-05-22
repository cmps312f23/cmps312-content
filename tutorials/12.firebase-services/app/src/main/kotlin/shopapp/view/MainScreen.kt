package shopapp.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import shopapp.view.components.NavDrawer
import shopapp.view.components.displayMessage
import shopapp.view.components.getCurrentRoute
import shopapp.view.navigation.AppNavigator
import shopapp.view.navigation.NavDestination
import shopapp.viewmodel.AuthViewModel

@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val authViewModel =
        viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    //Create a coroutine scope. Opening of Drawer and snackBar should happen in background thread without blocking main thread
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)
    val context = LocalContext.current
    var startDestination by remember { mutableStateOf(NavDestination.Login.route) }

    // LaunchedEffect will be executed when the composable is first launched
    // If the screen recomposes, the coroutine will NOT be re-executed
    LaunchedEffect(true) {
        // Get further details from Firestore about current user
        authViewModel.setCurrentUser()

        // Every time the Auth state changes display a message
        Firebase.auth.addAuthStateListener {
            println(">> Debug: Firebase.auth.addAuthStateListener: ${it.currentUser?.email}")
            val message = if (it.currentUser != null) {
                startDestination = NavDestination.ShoppingList.route
                "Welcome ${it.currentUser!!.displayName}"
            } else {
                "Sign out successful"
            }
            displayMessage(context, message = message)
        }
    }

    Scaffold(
        topBar = {
            // Do not show Top App Bar for the login screen
            if (currentRoute != NavDestination.Login.route) {
                TopBar(coroutineScope, drawerState)
            }
        },
        bottomBar = {
            // Do not show Bottom App Bar for the login screen
            if (currentRoute != NavDestination.Login.route)
                BottomNavBar(navController)
        },
        //drawerContent = { NavDrawer(navController, coroutineScope, scaffoldState) },
    ) { paddingValues ->
        NavDrawer(navController, coroutineScope, drawerState)
        AppNavigator(navController = navController, padding = paddingValues, startDestination = startDestination)
    }
}

/**
 * It receives navController to navigate between screens
 */
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)
        val navItems = listOf(NavDestination.ShoppingList, NavDestination.CloudStorage) //, Screen.Login)

        navItems.forEach { navItem ->
            NavigationBarItem(
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
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}

//A function which will receive a callback to trigger to opening the drawer
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(coroutineScope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(
        title = {
            Text(text = "Shopping App")
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = {
                    //When the icon is clicked open the drawer in coroutine scope
                    coroutineScope.launch {
                        drawerState.open()
                        //scaffoldState.drawerState.open()
                    }
                })
            )
        }
    )
}