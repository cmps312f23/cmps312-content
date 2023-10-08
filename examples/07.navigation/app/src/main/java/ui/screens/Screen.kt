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

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId: Int? = null) {
    data object Users : Screen(route = "users", title = "Users", icon = Icons.Outlined.Person)
    data object UserDetails : Screen(route = "user-details", title = "User Details", icon = Icons.Outlined.Person)

    data object Search : Screen(route = "search", title = "Search", icon = Icons.Outlined.Search)
    data object Apps : Screen(route = "apps", title = "Apps", icon = Icons.Outlined.List)

    //Nav Drawer items
    data object Profile : Screen(route = "profile", title = "Profile", icon = Icons.Outlined.Person)
    data object Addresses : Screen(route = "addresses", title = "Addresses", icon = Icons.Outlined.LocationOn)
    data object Orders : Screen(route = "orders", title = "Orders", icon = Icons.Outlined.ShoppingCart)
    data object Settings : Screen(route = "settings", title = "Settings", icon = Icons.Outlined.Settings)
    data object FAQ : Screen(route = "faq", title = "FAQ", icon = Icons.Outlined.Face)
    data object Divider : Screen(route = "", title = "Divider")
}