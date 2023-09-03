package json.country

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object CountryRepository {
    var countries = listOf<Country>()
    init {
        val filePath = "data/countries.json"
        val jsonData = getFileContent(filePath)
        //println(fileContent)

        val json = Json { ignoreUnknownKeys = true }
        countries = json.decodeFromString(jsonData)
    }

    fun getCountriesByContinent(continent: String) = countries.filter { it.continent.equals(continent, true) }
                                                              .sortedByDescending { it.population }

    fun getCountriesByRegion(region: String) = countries.filter { it.region.equals(region, true) }
                                                        .sortedBy { it.population }

    val countryCountByContinent = countries.groupingBy { it.continent }.eachCount()

    val populationByContinent = countries.groupingBy { it.continent }.fold(0) { sum: Long, country -> sum + country.population }

    fun getPopulousCountry() = countries.maxByOrNull { it.population }

    fun getLeastPopulatedCountry() = countries.filter { it.population > 0 }.minByOrNull { it.population }

    fun getLagestCountry() = countries.maxByOrNull { it.area }

    fun getSmallestCountry() = countries.filter { it.area > 0 } .minByOrNull { it.area }

    private fun getFileContent(filePath: String) : String {
        // Read file content
        /* Old fashion
          val bufferedReader = File(filePath).bufferedReader()
          return bufferedReader.readText()
        */
        /*
          We can invoke the use function on any object which implements AutoCloseable, just as with try-with-resources in Java.
          The method takes a lambda expression, executes it and disposes of the resource of (by calling close() on it)
          whenever execution leaves the block, either normally or with an exception.
        */
        // Better to use bufferedReader for reading a large file
        File(filePath).bufferedReader().use {
            return it.readText()
        }
    }
}