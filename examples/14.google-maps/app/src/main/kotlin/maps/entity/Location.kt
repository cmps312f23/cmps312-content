package maps.entity

data class Location(
    val name: String = "",
    val city: String = "",
    val country: String = "",
    val latitude: Double,
    val longitude: Double
) {
    override fun toString() = if (name.isNotEmpty()) "$name, $city, $country" else ""
}