package ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import compose.nav.R

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId: Int? = null) {
    data object Surahs: NavDestination(route = "quran", title = "Surahs", iconResourceId = R.drawable.ic_quran)
    data object Verses: NavDestination(route = "verses", title = "Verses", iconResourceId = R.drawable.ic_quran)

    data object Stats: NavDestination(route = "stats", title = "Stats", icon = Icons.Outlined.Info)
}