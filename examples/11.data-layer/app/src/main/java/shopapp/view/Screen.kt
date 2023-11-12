package shopapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object ShoppingList : Screen(route = "shopping-list", title = "Shopping List", icon = Icons.Outlined.ShoppingCart)
    object ShoppingItem : Screen(route = "shopping-item", title = "Shopping Item", icon = Icons.Outlined.ShoppingCart)
}