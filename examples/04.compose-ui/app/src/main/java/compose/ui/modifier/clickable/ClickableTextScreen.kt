package compose.ui.modifier.clickable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.ui.ui.theme.ComposeUITheme

@Composable
fun ClickableTextScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var count by remember { mutableStateOf(10) }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "-",
                modifier = Modifier
                    .border(2.dp, Color.Gray)
                    .padding(10.dp)
                    .clickable {
                        count -= 1
                    },
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = "$count", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "+",
                modifier = Modifier
                    .border(2.dp, Color.Gray)
                    .padding(10.dp)
                    .clickable {
                        count += 1
                    },
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
fun ClickableTextScreenPreview() {
    ComposeUITheme {
        ClickableTextScreen()
    }
}