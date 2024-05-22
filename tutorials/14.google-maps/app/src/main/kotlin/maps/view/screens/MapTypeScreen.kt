package maps.view.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapTypeScreen() {
    val quPosition = LatLng(25.37727951601785, 51.49117112159729)
    val zoomLevel = 20f // Buildings
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(quPosition, zoomLevel)
    }

    // Set properties using MapProperties which you can use to recompose the map
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(mapType = MapType.HYBRID)
        )
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = true, zoomControlsEnabled = true)
        )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = mapProperties, uiSettings = mapUiSettings,
        modifier = Modifier.fillMaxSize()
    ) {
        // A Snippet is a text displayed below the title
        val snippetText = "Lat: ${quPosition.latitude}, Long: ${quPosition.longitude}"
        Marker(
            state = MarkerState(position = quPosition),
            title = "Qatar University",
            snippet = snippetText
        )
    }

    MapTypeControls(onMapTypeClick = {
        Log.d("GoogleMap", "Selected map type $it")
        mapProperties = mapProperties.copy(mapType = it)
    })
}

@Composable
private fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(state = ScrollState(0)),
        horizontalArrangement = Arrangement.Center
    ) {
        MapType.values().forEach {
            MapTypeButton(type = it) { onMapTypeClick(it) }
        }
    }
}

@Composable
private fun MapTypeButton(type: MapType, onClick: () -> Unit) =
    MapButton(text = type.toString(), onClick = onClick)

@Composable
private fun MapButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            //backgroundColor = MaterialTheme.colorScheme.onPrimary,
            contentColor =  Color.Black //MaterialTheme.colorScheme.secondary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}
