package maps.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

private const val TAG = "AccessibilityActivity"


class AccessibilityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val singaporeState = rememberMarkerState(position = singapore)
            val cameraPositionState = rememberCameraPositionState {
                position = defaultCameraPosition
            }
            val uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
            val mapProperties by remember {
                mutableStateOf(MapProperties(mapType = MapType.NORMAL))
            }

            Box(Modifier.fillMaxSize()) {
                GoogleMap(
                    // mergeDescendants will remove accessibility from the entire map and content inside.
                    mergeDescendants = true,
                    // alternatively, contentDescription will deactivate it for the maps, but not markers.
                    contentDescription = "",
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                    uiSettings = uiSettings,
                    onPOIClick = {
                        Log.d(TAG, "POI clicked: ${it.name}")
                    }
                ) {
                    val markerClick: (Marker) -> Boolean = {
                        Log.d(TAG, "${it.title} was clicked")
                        cameraPositionState.projection?.let { projection ->
                            Log.d(TAG, "The current projection is: $projection")
                        }
                        false
                    }

                    Marker(
                        // contentDescription overrides title for TalkBack
                        contentDescription = "Description of the marker",
                        state = singaporeState,
                        title = "Marker in Singapore",
                        onClick = markerClick
                    )
                }
            }
        }
    }
}

