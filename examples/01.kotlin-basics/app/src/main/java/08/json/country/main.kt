package json.country

fun main() {
    println("> Top 5 populated countries by continent")
    CountryRepository.getCountriesByContinent("Asia").take(5).forEach(::println)

    println("\n> Top 5 least populated countries by region")
    CountryRepository.getCountriesByRegion("Western Asia").take(5).forEach(::println)

    println("\n> Country count by continent")
    val countryCountByContinent = CountryRepository.countryCountByContinent
    println(countryCountByContinent)

    println("\n> Population by continent")
    val populationByContinent = CountryRepository.populationByContinent
    populationByContinent.forEach {
        println("%-8s: %,15d".format(it.key, it.value))
    }

    println("\n> Population by continent sorted")
    populationByContinent
        //populationByContinent[it] is the value
        .toSortedMap(compareByDescending { populationByContinent[it] })
        .forEach {
            println("%-8s: %,15d".format(it.key, it.value))
        }

    println("\n> Country with the highest population")
    val populousCountry = CountryRepository.getPopulousCountry()
    println(populousCountry)

    println("\n> Country with least Population")
    val lowestPopulation = CountryRepository.getLeastPopulatedCountry()
    println(lowestPopulation)

    println("\n> Lagest country (with biggest area)")
    val largestCountry = CountryRepository.getLagestCountry()
    println(largestCountry)

    println("\n> Smallest country (with smallest area)")
    val smallestCountry = CountryRepository.getSmallestCountry()
    println(smallestCountry)
}