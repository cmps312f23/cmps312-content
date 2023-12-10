package maps.view.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import maps.components.LocationDropdown
import maps.repository.LocationRepository
import maps.viewmodel.MapViewModel
import maps.components.displayMessage

@Composable
fun PoIScreen() {
    var locationPermissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // Register request permission callback, which handles the user's response to the
    // system permission dialog.
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        // Callback for the result from requesting permission
        { permissions ->
            locationPermissionGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
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
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        requestPermissionLauncher.launch(locationPermissions)
    }

    var selectedPosition = LatLng(25.37727951601785, 51.49117112159729)
    val mapViewModel = viewModel<MapViewModel>()
    val locations = LocationRepository.getLocations()

    var selectedLocation by remember {
        mutableStateOf(locations[0])
    }

    val zoomLevel = 20f // Buildings
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedPosition, zoomLevel)
    }

    // Set properties using MapProperties which you can use to recompose the map
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.HYBRID,
                isMyLocationEnabled = locationPermissionGranted
            )
        )
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = true, zoomControlsEnabled = true)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LocationDropdown(
            label = "Locations",
            options = locations, selectedOption = selectedLocation,
            onSelectionChange = {
                selectedLocation = it
                selectedPosition = LatLng(it.latitude, it.longitude)
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(selectedPosition, zoomLevel)
                //mapViewModel.zoomToLocation(selectedLocation)
                //mapViewModel.addMarker(selectedLocation)
            })

        mapViewModel.deviceLocation?.let {
            val currentLocation =
                "\uD83D\uDCCD Device location Lat: ${it.latitude} & Long: ${it.longitude}"
            displayMessage(message = currentLocation)
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = currentLocation,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = mapProperties, uiSettings = mapUiSettings,
            modifier = Modifier.fillMaxSize()
        ) {
            // A Snippet is a text displayed below the title
            val snippetText =
                "Lat: ${selectedPosition.latitude}, Long: ${selectedPosition.longitude}"
            Marker(
                state = MarkerState(position = selectedPosition),
                title = "Qatar University",
                snippet = snippetText
            )
        }
    }
}