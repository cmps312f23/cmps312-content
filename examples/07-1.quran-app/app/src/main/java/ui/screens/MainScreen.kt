package ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ui.common.*
import ui.components.nav.AppNavigator
import ui.components.nav.BottomNavBar
import ui.theme.AppTheme

@Composable
fun MainScreen() {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    // For debugging - display the current route everytime the route changes
    val currentRoute = getCurrentRoute(navController) ?: ""
    displayMessage(LocalContext.current, currentRoute)

    Scaffold(
        topBar = {
            // Hide the TopBar for the User Details Screen & Verses Screen
            if (!currentRoute.startsWith(Screen.Verses.route)) {
                TopBar()
            }
        },
        bottomBar = { BottomNavBar(navController) }
    ) {
        paddingValues -> AppNavigator(navController = navController, padding = paddingValues)
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