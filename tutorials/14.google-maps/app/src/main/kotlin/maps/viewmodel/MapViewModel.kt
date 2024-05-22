package maps.viewmodel

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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import maps.components.displayMessage
import maps.entity.Location
import maps.entity.MenuOption
import java.util.*
import java.util.concurrent.TimeUnit

class MapViewModel(private val appContext: Application) : AndroidViewModel(appContext) {
    private val initialZoom = 15f // Street Level
    var zoom by mutableStateOf(initialZoom)

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    //private val deviceLocation = MutableLiveData<GeoLocation>()
    var deviceLocation by  mutableStateOf<LatLng?>(null)

    var poiMarker : Marker? = null

    // Checks that users have given permission to access the user's device current location
    fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    // Called whenever an item in the dropdown menu is clicked
    fun onMenuItemClick(menuOption: MenuOption) = when (menuOption) {
        // Change the map type based on the user's selection.
        /*MenuOption.MAP_NORMAL -> {
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
        }*/
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
            interval = TimeUnit.SECONDS.toMillis(30)
            fastestInterval = TimeUnit.SECONDS.toMillis(20)
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
}