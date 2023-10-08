package ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import compose.nav.R

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId: Int? = null) {
    object Users : Screen(route = "users", title = "Users", icon = Icons.Outlined.Person)
    object UserDetails : Screen(route = "user-details", title = "User Details", icon = Icons.Outlined.Person)

    object Search : Screen(route = "search", title = "Search", icon = Icons.Outlined.Search)
    object Apps : Screen(route = "apps", title = "Apps", icon = Icons.Outlined.List)

    //Nav Drawer items
    object Profile : Screen(route = "profile", title = "Profile", icon = Icons.Outlined.Person)
    object Addresses : Screen(route = "addresses", title = "Addresses", icon = Icons.Outlined.LocationOn)
    object Orders : Screen(route = "orders", title = "Orders", icon = Icons.Outlined.ShoppingCart)
    object Settings : Screen(route = "settings", title = "Settings", icon = Icons.Outlined.Settings)
    object FAQ : Screen(route = "faq", title = "FAQ", icon = Icons.Outlined.Face)
    object Divider : Screen(route = "", title = "Divider")

    object Quran: Screen(route = "ui", title = "Quran", iconResourceId = R.drawable.ic_quran)
    object Verses: Screen(route = "verses", title = "Surah Verses", iconResourceId = R.drawable.ic_quran)
}