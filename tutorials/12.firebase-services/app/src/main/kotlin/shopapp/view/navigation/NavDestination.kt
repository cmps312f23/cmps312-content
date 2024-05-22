package shopapp.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Minimize
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector) {
    data object ShoppingList : NavDestination(route = "shopping-list", title = "Shopping List", icon = Icons.Outlined.ShoppingCart)
    data object ShoppingItem : NavDestination(route = "shopping-item", title = "Shopping Item", icon = Icons.Outlined.ShoppingBag)
    data object CloudStorage : NavDestination(route = "storage", title = "Storage", icon = Icons.Outlined.CloudUpload)
    data object Signup : NavDestination(route = "signup", title = "Signup", icon = Icons.Outlined.PersonAdd)
    data object Login : NavDestination(route = "login", title = "Login", icon = Icons.Outlined.Login)
    data object Logout : NavDestination(route = "", title = "Logout", icon = Icons.Outlined.Logout)
    data object Divider : NavDestination(route = "", title = "Divider", icon = Icons.Outlined.Minimize)
}