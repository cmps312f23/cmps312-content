package shopapp.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import shopapp.view.navigation.AppNavigator

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    AppNavigator(navController)
}
