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

@Composable
fun SearchScreen() {
    ScreenContent(content = Screen.Search.title, icon = Screen.Search.icon)
}

@Composable
fun ProfileScreen() {
    ScreenContent(content = Screen.Profile.title, icon = Screen.Profile.icon)
}

@Composable
fun AddressesScreen() {
    ScreenContent(content = Screen.Addresses.title, icon = Screen.FAQ.icon)
}

@Composable
fun OrdersScreen() {
    ScreenContent(content = Screen.Orders.title, icon = Screen.Orders.icon)
}

@Composable
fun SettingsScreen() {
    ScreenContent(content = Screen.Settings.title, icon = Screen.Settings.icon)
}

@Composable
fun FAQScreen() {
    ScreenContent(content = Screen.FAQ.title, icon = Screen.FAQ.icon)
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

