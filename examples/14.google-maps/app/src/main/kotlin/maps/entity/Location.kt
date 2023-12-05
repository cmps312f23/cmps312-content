package maps.entity

import com.google.android.gms.maps.model.LatLng

data class Location(
    val name: String = "",
    val city: String = "",
    val country: String = "",
    val latitude: Double,
    val longitude: Double
) {
    val latLng
        get() = LatLng(this.latitude, this.longitude)

    override fun toString() = if (name.isNotEmpty()) "$name, $city, $country" else ""
}