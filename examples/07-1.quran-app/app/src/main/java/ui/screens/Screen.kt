package ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import compose.nav.R

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId: Int? = null) {
    data object Quran: Screen(route = "quran", title = "Quran", iconResourceId = R.drawable.ic_quran)
    data object Verses: Screen(route = "verses", title = "Surah Verses", iconResourceId = R.drawable.ic_quran)

    data object Stats: Screen(route = "stats", title = "Stats", icon = Icons.Outlined.Info)
}