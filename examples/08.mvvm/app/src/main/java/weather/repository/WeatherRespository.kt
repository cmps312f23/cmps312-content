package weather.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

data class Weather (val condition: String, val temperature: Float, val temperatureUnit: String = "Fahrenheit")

object WeatherRepository {
    private val weatherConditions = listOf("Sunny", "Windy", "Rainy", "Snowy")
    fun getWeather(): Flow<Weather> = flow {
        // Producer block
        while (true) {
            val condition = weatherConditions.shuffled().first()
            val temp = (Random.nextFloat() * 10) + (20..60).shuffled().first()
            val weather = Weather(condition, temp)
            emit(weather)
            delay(3000)
        }
    }
}