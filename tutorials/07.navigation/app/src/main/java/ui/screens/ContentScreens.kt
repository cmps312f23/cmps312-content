package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ui.navigation.NavDestination

@Composable
fun SearchScreen() {
    ScreenContent(content = NavDestination.Search.title, icon = NavDestination.Search.icon)
}

@Composable
fun ProfileScreen() {
    ScreenContent(content = NavDestination.Profile.title, icon = NavDestination.Profile.icon)
}

@Composable
fun AddressesScreen() {
    ScreenContent(content = NavDestination.Addresses.title, icon = NavDestination.FAQ.icon)
}

@Composable
fun OrdersScreen() {
    ScreenContent(content = NavDestination.Orders.title, icon = NavDestination.Orders.icon)
}

@Composable
fun SettingsScreen() {
    ScreenContent(content = NavDestination.Settings.title, icon = NavDestination.Settings.icon)
}

@Composable
fun FAQScreen() {
    ScreenContent(content = NavDestination.FAQ.title, icon = NavDestination.FAQ.icon)
}

@Composable
fun ScreenContent(content: String, icon: ImageVector?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = content,
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        Text(text = content)
    }
}

