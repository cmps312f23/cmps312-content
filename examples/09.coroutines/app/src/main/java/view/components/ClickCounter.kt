package coroutines.view.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ClickCounter(modifier: Modifier = Modifier) {
    var clicksCount by remember { mutableStateOf(0) }
    Button(
        modifier = modifier,
        onClick = { clicksCount++ }
    ) {
        Text("Clicked $clicksCount times")
    }
}