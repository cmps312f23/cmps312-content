package maps.view.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import maps.view.navigation.AppNavigator
import maps.view.navigation.NavDestination
import maps.view.navigation.getCurrentRoute

@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            //TopBar()
        },
        bottomBar = {
            BottomNavBar(navController)
        },
    ) { paddingValues ->
        AppNavigator(navController = navController, padding = paddingValues)
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
        val navItems = listOf(NavDestination.BasicMap,
                    NavDestination.MarkersClustering, NavDestination.MapType)

        navItems.forEach { navItem ->
            NavigationBarItem(
                //if currentRoute is equal to the nav item route then set selected to true
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
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
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Maps App")
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = {

                })
            )
        }
    )
}