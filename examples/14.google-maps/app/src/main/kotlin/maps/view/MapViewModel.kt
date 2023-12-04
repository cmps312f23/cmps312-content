package maps.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addGroundOverlay
import com.google.maps.android.ktx.addMarker
import google.maps.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import maps.components.MaxZoom
import maps.components.MinZoom
import maps.components.setZoom
import maps.entity.Location
import maps.entity.MenuOption
import qu.cmps312.maps.components.displayMessage
import java.util.*
import java.util.concurrent.TimeUnit

class MapViewModel(private val appContext: Application) : AndroidViewModel(appContext) {
    var googleMap : GoogleMap? = null

    private val initialZoom = 15f // Street Level
    var zoom by mutableStateOf(initialZoom)

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    //private val deviceLocation = MutableLiveData<GeoLocation>()
    var deviceLocation by  mutableStateOf<LatLng?>(null)

    var poiMarker : Marker? = null

    /**
     * Manipulates the map once available.
     * This function is called when the map is ready to be used.
     * This is where we can add markers or lines, add listeners
     * or zoom to a location.
     */
    fun onMapReady(location: Location) {
        zoomToLocation(location)
        addMarker(location)
        addOverlayImage(location)

        setOnPoiClick()
        setOnMapLongClick()
    }

    // Checks that users have given permission to access the user's device current location
    fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    // Checks if users have given their location and sets location enabled if so.
    @SuppressLint("MissingPermission")
    fun enableMyLocation() {
        if (isLocationPermissionGranted()) {
            googleMap?.isMyLocationEnabled = true

            // Get the user's device current location
            getCurrentLocation()
            // Subscribe to location updates
            startLocationUpdates()
        }
    }

    // Called when user makes a long press gesture on the googleMap.
    fun setOnMapLongClick() {
        googleMap?.setOnMapLongClickListener { latLng ->
            // A Snippet is Additional text that's displayed below the title
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            googleMap?.addMarker {
                position(latLng)
                title("Dropped Pin")
                snippet(snippet)
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            }
        }
    }

    // Places a marker on the map and displays an info window that contains POI name.
    fun setOnPoiClick() {
        googleMap?.setOnPoiClickListener { poi ->
            // A Snippet is Additional text that's displayed below the title.
            val snippet = "Lat:${poi.latLng.latitude}, Long: ${poi.latLng.longitude}"
            println(">> Debug. Clicked PoI placeId: ${poi.placeId}. Name: ${poi.name}. latLng: $snippet")

            // Remove previous poiMarker before creating a new one
            poiMarker?.remove()

            poiMarker = googleMap?.addMarker {
                position(poi.latLng)
                title(poi.name)
                snippet(snippet)
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            }
            poiMarker?.showInfoWindow()
        }
    }

    fun onZoomChange(zoomFactor: Float) {
        zoom *= zoomFactor
        // Ensures that the zoom value lies in the specified range
        zoom = zoom.coerceIn(MinZoom, MaxZoom)
        googleMap?.setZoom(zoom)
    }

    fun zoomToLocation(location: Location, zoomLevel: Float = initialZoom) {
        /* zoomLevel:
            1: World
            5: Continent
            10: City
            15: Streets
            20: Buildings
        */
        val latLng = LatLng(location.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel)
        googleMap?.moveCamera(cameraUpdate)
    }

    fun addMarker(location: Location) {
        // A Snippet is a text displayed below the title
        val snippetText = "Lat: ${location.latitude}, Long: ${location.longitude}"
        val latLng = LatLng(location.latitude, location.longitude)
        googleMap?.addMarker {
            position(latLng)
            title(location.name)
            snippet(snippetText)
        }?.showInfoWindow()
    }

    fun addOverlayImage(location: Location, overlaySize: Float = 100f, resourceId: Int = R.drawable.qu_logo) {
        val latLng = LatLng(location.latitude, location.longitude)
        // Add overlay image at the specified location
        googleMap?.addGroundOverlay {
            position(latLng, overlaySize)
            image(BitmapDescriptorFactory.fromResource(resourceId))
        }
    }

    // Called whenever an item in the dropdown menu is clicked
    fun onMenuItemClick(menuOption: MenuOption) = when (menuOption) {
        // Change the map type based on the user's selection.
        MenuOption.MAP_NORMAL -> {
            googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        MenuOption.MAP_HYBRID -> {
            googleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        MenuOption.MAP_SATELLITE -> {
            googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
        MenuOption.MAP_TERRAIN -> {
            googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
        MenuOption.REVERSE_GEOCODE -> {
            // Hamad International Airport, Doha, Qatar (25.260 , 51.6138)
            val hiaLat = 25.2609 //25.37727951601785 //-33.8885751183869
            val hiaLng = 51.6138 //51.49117112159729 //151.18734866380692
            val hiaLocation = getLocation(hiaLat, hiaLng)
            val message = "Lat: $hiaLat & Long: $hiaLng is $hiaLocation"
            displayMessage(appContext, message)
        }
        MenuOption.GEOCODE -> {
            // Hamad International Airport, Doha, Qatar (25.260 , 51.6138)
            val locationName = "Hamad International Airport"
            val geoLocation = getGeoCoordinates(locationName)
            geoLocation?.let {
                val message = "$locationName @ Lat: ${it.latitude} & Long: ${it.longitude}"
                displayMessage(appContext, message)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        viewModelScope.launch {
            val lastLocation = fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                deviceLocation = LatLng(location.latitude, location.longitude)
                println(">> Debug: Lat: ${location.latitude} & Long: ${location.longitude}")
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun FusedLocationProviderClient.locationFlow(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(Constants.UPDATE_INTERVAL_SECS)
            fastestInterval = TimeUnit.SECONDS.toMillis(Constants.FASTEST_UPDATE_INTERVAL_SECS)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                val userLocation = location?.let {
                    Location(
                        latitude = it.latitude,
                        longitude = location.longitude
                    )
                }
                try {
                    if (userLocation != null) {
                        this@callbackFlow.trySend(userLocation).isSuccess
                    }
                    removeLocationUpdates(this)
                } catch (e: Exception) {
                }
            }
        }

        requestLocationUpdates(
            locationRequest,
            callBack,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e)
        }
        awaitClose {
            removeLocationUpdates(callBack)
        }
    }

    /*@SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000 // every 10 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
         fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            locationResult.locations.forEach {
                if (deviceLocation != LatLng(it.latitude, it.longitude)) {
                    deviceLocation = LatLng(it.latitude, it.longitude)
                    println(">> Debug: Lat: ${it.latitude} & Long: ${it.longitude}")
                    //zoomToLocation(Location(latitude = it.latitude, longitude = it.longitude))
                }
            }
        }
    }*/

    /*
        Reverse geocoding = converting geographic coordinates (lat, lng)
        into a human-readable location address
    */
    fun getLocation(lat: Double, lng: Double): Location? {
        val geocoder = Geocoder(appContext)
        val locations = geocoder.getFromLocation(lat, lng, 1)

        return if (locations!= null && locations.size > 0) {
            val name = locations[0]?.featureName ?: ""
            val city = locations[0]?.locality ?: ""
            val country = locations[0]?.countryName ?: ""
            Location(name, city, country, lat, lng)
        } else {
            null
        }
    }

    /*
       Geocoding = converting an address or location name (like a street address) into
       geographic coordinates (lat, lng)
   */
    fun getGeoCoordinates(locationAddress: String): LatLng? {
        val geocoder = Geocoder(appContext)
        val coordinates = geocoder.getFromLocationName(locationAddress, 1)
        return if (coordinates != null && coordinates.size > 0) {
            val latitude = coordinates[0].latitude
            val longitude = coordinates[0].longitude
            LatLng(latitude, longitude)
        } else {
            null
        }
    }

    override fun onCleared() {
        super.onCleared()
        googleMap?.clear()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}