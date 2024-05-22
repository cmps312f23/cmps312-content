package demos.simple.nav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.screens.OrdersScreen
import ui.screens.ProfileScreen
import ui.screens.SettingsScreen

@Composable
fun SimpleNav() {
    val navController = rememberNavController()

    Column {
        Row {
            Button(onClick = {
                navController.navigate("profile")
            }) {
                Text("Profile")
            }
            Button(onClick = {
                navController.navigate("orders")
            }) {
                Text("Orders")
            }

            Button(onClick = {
                navController.navigate("settings")
            }) {
                Text("Settings")
            }
        }

        NavHost(navController = navController,
            startDestination = "profile"
        ) {
            composable("profile") {
                ProfileScreen()
            }

            composable("orders") {
                OrdersScreen()
            }

            composable("settings") {
                SettingsScreen()
            }
        }
    }
}

@Preview
@Composable
fun SimpleNavPreview() {
    SimpleNav()
}