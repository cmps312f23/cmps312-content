package football.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import football.repository.Weather
import football.repository.WeatherRepository
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@SuppressLint("LongLogTag")
class WeatherViewModel : ViewModel() {
     private val TAG = "LifeCycle->WeatherViewModel ✔"
    /*
     - Trigger the flow and start listening for values.
     - Note that this happens when lifecycle is STARTED
     - and stops collecting when the lifecycle is STOPPED
     - Flow will give you a new value every time a new value is emitted.
     A StateFlow, in addition to what a Flow is, it always holds the last value
     */
    // You can use WhileSubscribed(5000) to keep the upstream flow active for
    // 5 seconds more after the disappearance of the last collector.
    // That avoids restarting the upstream flow after a configuration change
    val weatherFlow : StateFlow<Weather?> = WeatherRepository.getWeather().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )

    val weatherCelsiusFlow = weatherFlow.map {
        it?.let { weather ->
            Weather(
                weather.condition,
                (weather.temperature - 32) * 5 / 9,
                "Celsius",
            )
        }
    }

    val weatherIconFlow = weatherFlow.map {
        it?.let { weather ->
            when(weather.condition) {
                "Sunny" -> "☀️"
                "Windy"  -> "\uD83C\uDF43"
                "Rainy"  -> "⛈️️"
                "Snowy"  -> "❄️"
                else -> ""
            }
        }
    }

    init {
        Log.d(TAG, "Created")
        weatherFlow.onEach { Log.d(TAG, it?.condition ?: "") }
    }
}