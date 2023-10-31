package football.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class Weather (val condition: String, val temperature: Float, val temperatureUnit: String = "Fahrenheit")

object WeatherRepository {
    private val weatherConditions = listOf("Sunny", "Windy", "Rainy", "Snowy")
    fun getWeather(): Flow<Weather> = flow {
        while (true) {
            delay(3000)
            val condition = weatherConditions.shuffled().first()
            val temp = (1..50).shuffled().first().toFloat()
            val weather = Weather(condition, temp)
            emit(weather)
        }
    }
}