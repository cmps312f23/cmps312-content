package compose.ui.state

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnrememberedMutableState")
@Composable
fun ClicksCounterScreen() {
    //var clickCount = 0
    var clickCount by remember {
        mutableStateOf(0)
    }
    Column {
        ClickCounter() //clicks = clickCount, onClick = { clickCount++ })
        Text("Counter $clickCount")
    }
}

@Composable
fun ClickCounter () { //(clicks: Int, onClick: () -> Unit) {
    var clicks by remember {
        mutableStateOf(0)
    }
    Button(
        onClick = {clicks++},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text("I've been clicked $clicks times")
    }
}

@Preview
@Composable
fun ClicksCounterScreenPreview() {
    ClicksCounterScreen()
}