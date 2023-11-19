package qu.lingosnacks.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import qu.lingosnacks.R
import qu.lingosnacks.repository.PackagesRepository
import qu.lingosnacks.repository.UserRepository
import qu.lingosnacks.ui.navigation.AppNavigator
import qu.lingosnacks.ui.navigation.BottomBar
import qu.lingosnacks.ui.navigation.NavDestination
import qu.lingosnacks.ui.navigation.TopBar
import qu.lingosnacks.utils.getPreviousScreenRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    UserRepository.initUsers(LocalContext.current)
    PackagesRepository.initPackages(LocalContext.current)
    var user by rememberSaveable {
        mutableStateOf(UserRepository.currentUser?.email ?: "t2@test.com")
    }
    var allPackages by rememberSaveable { mutableStateOf(PackagesRepository.packages.toList()) }
    var userPackages by rememberSaveable {
        mutableStateOf(PackagesRepository.getPackagesByAuthor(user))
    }

    //Screens that don't have a bottom navigation bar
    val hideNavBarNavDestinations = listOf(
        NavDestination.LoginScreen,
        NavDestination.SignupScreen
    )

    Scaffold(
        topBar = {
            if (hideNavBarNavDestinations.none { it.route == currentRoute })
                TopBar(navController, currentRoute)
        },
        bottomBar = {
            if (hideNavBarNavDestinations.none { it.route == currentRoute })
                BottomBar(navController, currentRoute)
        },
        modifier = modifier
    ) {
        AppNavigator(
            user,
            allPackages,
            userPackages,
            {
                user = UserRepository.currentUser?.email ?: "t2@test.com"
                userPackages = PackagesRepository.getPackagesByAuthor(user)
            },
            {
                allPackages =
                    if (it.isBlank()) PackagesRepository.packages
                    else PackagesRepository.searchPackages(it.trim())
            },
            {
                PackagesRepository.deletePackage(it)
                allPackages = PackagesRepository.packages.toList()
                userPackages = PackagesRepository.getPackagesByAuthor(user)
            }, { type, packageObj ->
                if (type == "add")
                    PackagesRepository.addPackage(packageObj)
                else
                    PackagesRepository.updatePackage(packageObj)
                allPackages = PackagesRepository.packages.toList()
                userPackages = PackagesRepository.getPackagesByAuthor(user)
            },
            navController, it
        )
    }
}

fun NavHostController.navigateNewStateTo(route: String) {
    println("navigateNewStateTo: $route")
    this.navigate(route) {
        popUpTo(
            this@navigateNewStateTo.graph.findStartDestination().id
        )
        launchSingleTop = true
    }
}