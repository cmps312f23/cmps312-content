package maps.view.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import maps.components.displayMessage

@Composable
fun LocationPermissionScreen() {
    var locationPermissionGranted by remember { mutableStateOf(false ) }
    val context = LocalContext.current
    // Register request permission callback, which handles the user's response to the
    // system permission dialog.
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        // Callback for the result from requesting permission
        { permissions ->
            locationPermissionGranted = permissions.values.reduce {
                    acc, isPermissionGranted -> acc && isPermissionGranted
            }
            if (locationPermissionGranted) {
                val message = "Location permission granted"
                displayMessage(context, message)
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision.
                val message = "Location permission denied"
                displayMessage(context, message)
            }
        }

    LaunchedEffect(Unit) {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissionLauncher.launch(locationPermissions)
    }

    val quPosition = LatLng(25.37727951601785, 51.49117112159729)
    val zoomLevel = 20f // Buildings
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(quPosition, zoomLevel)
    }

    // Set properties using MapProperties which you can use to recompose the map
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(mapType = MapType.HYBRID,
                isMyLocationEnabled = locationPermissionGranted)
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
}