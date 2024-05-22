package compose.ui.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme

@Composable
fun BoxLayoutScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.weight(1F).fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .border(2.dp, Color.Gray)
                    .padding(16.dp),
                text = "Aligned to bottom end"
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .border(2.dp, Color.Gray)
                    .padding(16.dp),
                text = "Aligned to bottom start"
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .border(2.dp, Color.Gray)
                    .padding(16.dp),
                text = "Aligned to start center "
            )
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .border(2.dp, Color.Gray)
                    .padding(16.dp),
                text = "Aligned to top center "
            )
        }

        Box(modifier = Modifier.weight(1F).fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Column Text 1")
                Text("Column Text 2")
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Row Text 1")
                    Text(text = "Row Text 2")
                }
            }

            Text(
                text = "Stack Text",
                modifier = Modifier.align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 16.dp)
            )

        }
    }
}

@Preview
@Composable
fun BoxLayoutScreenPreview() {
    AppTheme {
        BoxLayoutScreen()
    }
}