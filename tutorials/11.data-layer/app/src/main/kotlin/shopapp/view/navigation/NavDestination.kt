package shopapp.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector) {
    data object ShoppingList : NavDestination(route = "shopping-list", title = "Shopping List", icon = Icons.Outlined.ShoppingCart)
    data object ShoppingItem : NavDestination(route = "shopping-item", title = "Shopping Item", icon = Icons.Outlined.ShoppingCart)
}