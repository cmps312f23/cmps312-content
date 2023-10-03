package compose.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResponsiveScreen() {
    Column(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF8DB600))
                .weight(1F)
        ) {
            Text( modifier = Modifier.align(Alignment.Center), text = "Height: 1F -> 1/10 of space")
        }
        Row(Modifier.weight(8F).fillMaxWidth()) {
            Box(
                Modifier
                    .weight(3F)
                    .background(Color.Cyan)
                    .fillMaxHeight()
                ){
                Text( modifier = Modifier.align(Alignment.Center), text = "Height: 8F -> 8/10 of space\r\nWidth: 3F -> 3/4 of space")
            }
            Box(
                Modifier
                    .weight(1F)
                    .background(Color.Yellow)
                    .fillMaxHeight()
                ){
                Text( modifier = Modifier.align(Alignment.Center), text = "Height: 8F -> 8/10 of space\r\nWidth: 1F -> 1/4 of space")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(1F)
        ){
            Text( modifier = Modifier.align(Alignment.Center), text = "Height: 1F -> 1/10 of space")
        }
    }
}

@Preview
@Composable
fun ResponsiveScreenPreview() {
    ResponsiveScreen()
}
