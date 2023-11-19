package qu.lingosnacks.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import qu.lingosnacks.repository.UserRepository
import qu.lingosnacks.ui.navigateNewStateTo
import qu.lingosnacks.utils.getPreviousScreenRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, currentRoute: String?) {
    val navDestinations = listOf(
        NavDestination.LoginScreen,
        NavDestination.SignupScreen,
        NavDestination.PackagesScreen,
        NavDestination.MyPackagesScreen
    )

    val previousScreen = getPreviousScreenRoute(navController)

    //So that the user doesn't go back to login screen without logging out, log out only using dedicated button
    val allowGoingBack =
        previousScreen != NavDestination.LoginScreen.route && previousScreen != NavDestination.SignupScreen.route

    TopAppBar(
        title = {
            Text(
                navDestinations.find { it.route == currentRoute }?.title ?: "LingoSnacks",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                enabled = allowGoingBack
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                UserRepository.logout()
                //"default" email when user is null
                //never used because a user is always logged in
                //to access the other pages of the app
                //user = "t2@test.com"
                navController.navigateNewStateTo(NavDestination.LoginScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
    )
}