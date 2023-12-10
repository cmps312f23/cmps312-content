package maps.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.WhereToVote
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector) {
    data object BasicMap : NavDestination(route = "basic-map", title = "Basic Map", icon = Icons.Outlined.LocationOn)
    data object MarkersClustering : NavDestination(route = "markers-clustering", title = "Clustering", icon = Icons.Outlined.Groups)
    data object MapType : NavDestination(route = "map-type", title = "MapType", icon = Icons.Outlined.Map)
    data object LocationPermission : NavDestination(route = "location-permission", title = "Loc Permission", icon = Icons.Outlined.WhereToVote)
}