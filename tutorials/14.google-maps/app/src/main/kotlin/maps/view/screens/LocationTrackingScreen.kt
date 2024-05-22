package maps.view.screens

import android.location.Location
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlin.random.Random

/**
 * This shows how to use a custom location source to show a blue dot on the map based on your own
 * locations.
 */
@Composable
fun LocationTrackingScreen() {
    val quPosition = LatLng(25.37727951601785, 51.49117112159729)
    val initialCameraPosition = CameraPosition.fromLatLngZoom(quPosition, 11f)

    val lifecycleScope = rememberCoroutineScope()
    val TAG = "LocationTrackingScreen"
    val locationSource = MyLocationSource()
    var counter = 0
    val zoom = 20F

    var locationFlow : SharedFlow<Location>? = null

    LaunchedEffect(Unit) {
        // Generates "fake" locations randomly every 2 seconds.
        // Normally you'd request location updates:
        // https://developer.android.com/training/location/request-updates
        locationFlow = callbackFlow {
            while (true) {
                ++counter

                val location = newLocation()
                Log.d(TAG, "Location $counter: $location")
                trySend(location)

                delay(2_000)
            }
        }.shareIn(
            lifecycleScope,
            replay = 0,
            started = SharingStarted.WhileSubscribed()
        )
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    // To control and observe the map camera
    val cameraPositionState = rememberCameraPositionState {
        position = initialCameraPosition
    }

    // To show blue dot on map
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }

    // Collect location updates
    val locationState = locationFlow?.collectAsState(initial = newLocation())?.value ?: newLocation()

    // Update blue dot and camera when the location changes
    LaunchedEffect(locationState) {
        Log.d(TAG, "Updating blue dot on map...")
        locationSource.onLocationChanged(locationState)

        Log.d(TAG, "Updating camera position...")
        val cameraPosition = CameraPosition.fromLatLngZoom(
            LatLng(
                locationState.latitude,
                locationState.longitude
            ), zoom
        )
        cameraPositionState.animate(
            CameraUpdateFactory.newCameraPosition(cameraPosition),
            1_000
        )
    }

    // Detect when the map starts moving and print the reason
    LaunchedEffect(cameraPositionState.isMoving) {
        if (cameraPositionState.isMoving) {
            Log.d(
                TAG,
                "Map camera started moving due to ${cameraPositionState.cameraMoveStartedReason.name}"
            )
        }
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            },
            // This listener overrides the behavior for the location button. It is intended to be used when a
            // custom behavior is needed.
            onMyLocationButtonClick = {
                Log.d(
                    TAG,
                    "Overriding the onMyLocationButtonClick with this Log"
                ); true
            },
            locationSource = locationSource,
            properties = mapProperties
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize()
                )
            }
        }
    }
}

/**
 * A [LocationSource] which allows it's location to be set. In practice you would request location
 * updates (https://github.com/android/platform-samples/blob/main/samples/location/src/main/java/com/example/platform/location/currentLocation/CurrentLocationScreen.kt).
 */
class MyLocationSource : LocationSource {
    var listener: LocationSource.OnLocationChangedListener? = null

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        this.listener = listener
    }

    override fun deactivate() {
        listener = null
    }

    fun onLocationChanged(location: Location) {
        listener?.onLocationChanged(location)
    }
}

fun newLocation(): Location {
    val location = Location("MyLocationProvider")
    location.apply {
        latitude = quPosition.latitude + Random.nextFloat()
        longitude = quPosition.longitude + Random.nextFloat()
    }
    return location
}