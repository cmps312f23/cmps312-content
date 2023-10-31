package football.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import football.view.theme.AppTheme
import football.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(weatherViewModel : WeatherViewModel) {
    //collectAsStateWithLifecycle() collects values from a Flow in a lifecycle-aware
    // manner, allowing the app to save unneeded app resources.
    // It represents the latest emitted value as a Compose State.
    val weatherUpdate = weatherViewModel.weatherFlow.collectAsStateWithLifecycle(null)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Recomposes whenever newsUpdate changes
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            text = "\uD83D\uDCE2 ${weatherUpdate?.value}",
        )
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