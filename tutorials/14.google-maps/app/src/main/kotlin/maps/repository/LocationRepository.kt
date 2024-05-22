package maps.repository

import maps.entity.Location

object LocationRepository {
    fun getLocations() = listOf(
        Location(
            name = "Qatar University",
            city = "Doha",
            country = "Qatar",
            latitude = 25.37727951601785,
            longitude = 51.49117112159729
        ),
        Location(
            name = "Hamad International Airport",
            city = "Doha",
            country = "Qatar",
            latitude = 25.260,
            longitude = 51.6138
        ),
        Location(
            name = "Museum of Islamic Art",
            city = "Doha",
            country = "Qatar",
            latitude = 25.295535181463016,
            longitude = 51.53918266296387
        )
    )
}