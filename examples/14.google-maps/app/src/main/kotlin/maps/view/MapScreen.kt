package maps.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.MapView
import com.google.maps.android.ktx.awaitMap
import maps.components.LocationDropdown
import maps.components.DropDownMenu
import maps.components.rememberMapView
import maps.entity.Location
import maps.repository.LocationRepository
import qu.cmps312.maps.components.displayMessage

@Composable
fun MapScreen() {
    val mapViewModel = viewModel<MapViewModel>()

    val locations = LocationRepository.getLocations()

    var selectedLocation by remember {
        mutableStateOf(locations[0])
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LocationDropdown(
            label = "Locations",
            options = locations, selectedOption = selectedLocation,
            onSelectionChange = {
                selectedLocation = it
                mapViewModel.zoomToLocation(selectedLocation)
                mapViewModel.addMarker(selectedLocation)
            })

        mapViewModel.deviceLocation?.let {
            val currentLocation = "\uD83D\uDCCD Device location Lat: ${it.latitude} & Long: ${it.longitude}"
            displayMessage(message = currentLocation)
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = currentLocation,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        // When an update to the MapView happens, the MapView won't need to be recreated
        val mapView = rememberMapView()
        MapViewContainer(mapViewModel, mapView, selectedLocation)
    }
}

@Composable
private fun MapViewContainer(
    mapViewModel : MapViewModel,
    mapView: MapView,
    location: Location
) {
    val context = LocalContext.current

    // Register request permission callback, which handles the user's response to the
    // system permission dialog.
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())
        // Callback for the result from requesting permission
        { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Enable My Location button on the map
                mapViewModel.enableMyLocation()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision.
                val message = "The following features will be missing: (1) My Location button on the map (2) "
                displayMessage(context, message = message, Toast.LENGTH_SHORT)
            }
        }

    // Checks if users have given their location and sets location enabled if so.
    fun requestPermissionToEnableMyLocation() {
        if (mapViewModel.isLocationPermissionGranted()) {
            mapViewModel.enableMyLocation()
        } else {
            // Ask for the permission to access the user's device location
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(mapView) {
        val googleMap = mapView.awaitMap()
        mapViewModel.googleMap = googleMap
        mapViewModel.onMapReady(location)
        requestPermissionToEnableMyLocation()
    }

    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        ZoomButton("-", onClick = { mapViewModel.onZoomChange(0.8f) })
        ZoomButton("+", onClick = { mapViewModel.onZoomChange(1.2f) })

        Spacer(modifier = Modifier.padding(start = 16.dp))
        DropDownMenu(onMenuItemClick = {
            mapViewModel.onMenuItemClick(it)
        })
    }

    AndroidView({ mapView })
}

@Composable
private fun ZoomButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        onClick = onClick
    ) {
        Text(text = text)
    }
}