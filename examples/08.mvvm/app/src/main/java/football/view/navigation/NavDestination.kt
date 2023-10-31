package compose.nav.football.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector) {
    object Score: NavDestination(route = "score", title = "Score", icon = Icons.Outlined.Home)
    object Users: NavDestination(route = "users", title = "Users", icon = Icons.Outlined.AccountCircle)
    object Weather: NavDestination(route = "weather", title = "Weather", icon = Icons.Outlined.Info)
}