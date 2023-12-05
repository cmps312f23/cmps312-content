package maps.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector) {
    data object BasicMap : NavDestination(route = "basic-map", title = "Basic Map", icon = Icons.Outlined.LocationOn)
    data object MarkersClustering : NavDestination(route = "markers-clustering", title = "Clustering", icon = Icons.Outlined.List)
}