package ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId: Int? = null) {
    data object Users : NavDestination(route = "users", title = "Users", icon = Icons.Outlined.Person)
    data object UserDetails : NavDestination(route = "user-details", title = "User Details", icon = Icons.Outlined.Person)

    data object Search : NavDestination(route = "search", title = "Search", icon = Icons.Outlined.Search)
    data object Apps : NavDestination(route = "apps", title = "Apps", icon = Icons.Outlined.List)

    //Nav Drawer items
    data object Profile : NavDestination(route = "profile", title = "Profile", icon = Icons.Outlined.Person)
    data object Addresses : NavDestination(route = "addresses", title = "Addresses", icon = Icons.Outlined.LocationOn)
    data object Orders : NavDestination(route = "orders", title = "Orders", icon = Icons.Outlined.ShoppingCart)
    data object Settings : NavDestination(route = "settings", title = "Settings", icon = Icons.Outlined.Settings)
    data object FAQ : NavDestination(route = "faq", title = "FAQ", icon = Icons.Outlined.Face)
    data object Divider : NavDestination(route = "", title = "Divider")
}