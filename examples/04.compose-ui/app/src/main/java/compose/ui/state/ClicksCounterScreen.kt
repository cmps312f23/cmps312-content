package compose.ui.state

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ClicksCounterScreen() {
    var clicksCount by remember { mutableStateOf(0) }
    ClickCounter(clicks = clicksCount, onClick = { clicksCount += 1 })
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
    ) {
        Text("I've been clicked $clicks times")
    }
}

@Preview
@Composable
fun ClicksCounterScreenPreview() {
    ClicksCounterScreen()
}