package ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ui.common.*
import ui.navigation.AppNavigationRail
import ui.navigation.AppNavigator
import ui.navigation.BottomNavBar
import ui.navigation.NavDestination
import ui.theme.AppTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current as Activity
    val windowSizeClass = calculateWindowSizeClass(context)

    val shouldShowBottomBar =  windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val shouldShowNavRail = !shouldShowBottomBar


    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    // For debugging - display the current route everytime the route changes
    val currentRoute = getCurrentRoute(navController) ?: ""
    displayMessage(LocalContext.current, currentRoute)

    Scaffold(
        topBar = {
            // Hide the TopBar for the User Details Screen & Verses Screen
            if (!currentRoute.startsWith(NavDestination.Verses.route)) {
                TopBar()
            }
        },
        bottomBar = {
            if (shouldShowBottomBar)
                BottomNavBar(navController)
        }
    ) {
        padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            if (shouldShowNavRail) {
                AppNavigationRail(navController)
            }
            AppNavigator(navController = navController) //, padding = paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "القرآن الكريم",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}