package compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SliderScreen() {
    var red by remember { mutableStateOf(152) }
    var blue by remember { mutableStateOf(251) }
    var green by remember { mutableStateOf(152) }

    Column {
        ColorSlider(colorName = "Red", colorValue = red, onValueChange = { red = it })
        ColorSlider(colorName = "Blue", colorValue = blue, onValueChange = { blue = it })
        ColorSlider(colorName = "Green", colorValue = green, onValueChange = { green = it })
        Box(modifier = Modifier.fillMaxSize().background(color = Color(red, blue, green)))
    }
}

@Composable
fun ColorSlider(colorName: String, colorValue: Int, onValueChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = colorName, modifier = Modifier.weight(1.5F))
        Slider(
            value = colorValue/100f,
            onValueChange = { onValueChange((it * 100).roundToInt()) },
            valueRange = 0.0f..2.55f,
            modifier = Modifier.weight(8F)
        )
        Text(
            text = "$colorValue",
            modifier = Modifier.weight(1F)
        )
    }
}

@Preview
@Composable
fun SliderScreenPreview() {
    SliderScreen()
}