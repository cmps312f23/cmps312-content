package weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.nav.R
import football.view.theme.AppTheme
import weather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(weatherViewModel : WeatherViewModel) {
    //collectAsStateWithLifecycle() collects values from a Flow in a lifecycle-aware
    // manner, allowing the app to save unneeded app resources.
    // It represents the latest emitted value as a Compose State.
    val weatherUpdate = weatherViewModel.weatherFlow.collectAsStateWithLifecycle(null)
    val weatherCelsiusUpdate = weatherViewModel.weatherCelsiusFlow.collectAsStateWithLifecycle(null)
    val weatherIcon = weatherViewModel.weatherIconFlow.collectAsStateWithLifecycle("")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.weather),
            contentDescription = "Weather Icon",
            modifier = Modifier.padding(bottom = 80.dp).height(80.dp)
        )

        weatherUpdate.value?.let {
            // Recomposes whenever newsUpdate changes
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                text = "️${weatherIcon.value} ${it.condition} ${it.temperature} ${it.temperatureUnit}",
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                text = "️${weatherIcon.value} ${weatherCelsiusUpdate.value?.condition} ${weatherCelsiusUpdate.value?.temperature} ${weatherCelsiusUpdate.value?.temperatureUnit}",
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    AppTheme {
        val weatherViewModel = viewModel<WeatherViewModel>()
        WeatherScreen(weatherViewModel)
    }
}