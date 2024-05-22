package maps.view.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

private const val TAG = "MapClustering"
val quPosition = LatLng(25.37727951601785, 51.49117112159729)
val hamadAirportPosition = LatLng(28.260, 51.6138)
val islamicMuseumPosition = LatLng(27.295535181463016, 51.53918266296387)
val hamadStadiumPosition = LatLng(26.2511339955, 51.5345478618)

@Composable
fun MapMarkersClustering() {
    val items = remember { mutableStateListOf<MapClusterItem>() }
    LaunchedEffect(Unit) {
        items.add(MapClusterItem(quPosition, "Qatar University"))
        items.add(MapClusterItem(hamadAirportPosition, "Hamad International Airport"))
        items.add(MapClusterItem(islamicMuseumPosition, "Museum of Islamic Art"))
        items.add(MapClusterItem(hamadStadiumPosition, "Hamad bin Khalifa Stadium"))
        items.add(MapClusterItem(LatLng(24.420738, 51.490154), "Lusail Stadium"))
        items.add(MapClusterItem(LatLng(25.3607, 51.4811), "University of Doha for Science and Technology"))
    }
    MapMarkersClustering(items = items)
}

@Composable
fun MapMarkersClustering(items: List<MapClusterItem>) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(quPosition, 10f)
        }
    ) {
        Clustering(
            items = items,
            // Optional: Handle clicks on clusters, cluster items, and cluster item info windows
            onClusterClick = {
                Log.d(TAG, "Cluster clicked! $it")
                false
            },
            onClusterItemClick = {
                Log.d(TAG, "Cluster item clicked! $it")
                false
            },
            onClusterItemInfoWindowClick = {
                Log.d(TAG, "Cluster item info window clicked! $it")
            },
            // Optional: Custom rendering for non-clustered items
            clusterItemContent = null
        )
    }
}

data class MapClusterItem(
    val itemPosition: LatLng,
    val itemTitle: String,
    val itemSnippet: String = itemTitle,
    val itemZIndex: Float = 0F,
) : ClusterItem {
    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet

    override fun getZIndex(): Float =
        itemZIndex
}
