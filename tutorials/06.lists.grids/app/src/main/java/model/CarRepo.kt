package model

object CarRepo {
    private val carBrandsMap =  mapOf(
        "Toyota" to "https://www.carlogos.org/car-logos/toyota-logo-2020-europe-download.png",
        "Honda" to "https://www.carlogos.org/car-logos/honda-logo-2000-full-download.png",
        "Ford" to "https://www.carlogos.org/car-logos/ford-logo-2017-download.png",
        "Kia" to "https://www.carlogos.org/logo/Kia-logo-2560x1440.png",
        "Volkswagen" to "https://www.carlogos.org/logo/Volkswagen-logo-2019-1500x1500.png",
        "BMW" to "https://www.carlogos.org/car-logos/bmw-logo-2020-gray-download.png",
        "Mercedes-Benz" to "https://www.carlogos.org/logo/Mercedes-Benz-logo-2011-1920x1080.png",
        "Tesla" to "https://www.carlogos.org/car-logos/tesla-logo-2007-full-download.png",
        "Nissan" to "https://www.carlogos.org/car-logos/nissan-logo-2020-black.png",
        "Hyundai" to "https://www.carlogos.org/car-logos/hyundai-logo-2011-download.png",
        "General Motors (GM)" to "https://www.carlogos.org/logo/General-Motors-logo-2010-3300x3300.png",
        "Volvo" to "https://www.carlogos.org/logo/Volvo-logo-2014-1920x1080.png",
        "Renault" to "https://www.carlogos.org/logo/Renault-logo-2015-2048x2048.png"
    )

    val carBrands
        get() = carBrandsMap.keys.toList()

    fun getBrandLogoUrl(carBrand: String) = carBrandsMap[carBrand] ?: ""
}