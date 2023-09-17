package compose.ui.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.ui.R

fun getRandomColor() : Int {
    val rgbValues = (0..255).shuffled().take(4)
    return android.graphics.Color.argb(rgbValues[0], rgbValues[1], rgbValues[2], rgbValues[3])
}

@Composable
fun WelcomeScreen() {
    // State in an app = any value that can change over time
    // State hoisting in Compose is a pattern of moving state to
    // the caller to make a composable stateless
    var name by remember { mutableStateOf("Android") }
    Column(
        modifier = Modifier.fillMaxWidth()
                           .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        NameEditor(name = name, onNameChange = { name = it })
        Spacer(modifier = Modifier.size(16.dp))
        Welcome(name)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameEditor(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Your name") }
    )
}

@Composable
fun Welcome(name: String) {
    // Internal state. It is observed => when changed to UI part that uses it get recomposed
    // UI = f(state)
    var backgroundColor by remember { mutableStateOf(Color.White) }
    var count by remember { mutableStateOf(0) }

    // Modifiers modify the composable that its applied to.
    // In this example, we configure the column to have a padding of 16 dp
    val padding = 16.dp
    Column(
        modifier = Modifier
            .padding(padding)
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        // Image is a pre-defined composable to display an image
        Image(
            painter = painterResource(R.drawable.img_yahala),
            contentDescription = "Yahala Image",
            modifier = Modifier
                .height(200.dp)
                .padding(padding)
        )
        Spacer(Modifier.size(padding))
        Text(text = "Welcome $name!")
        Spacer(Modifier.size(padding))
        Button(onClick = {
            backgroundColor = Color(getRandomColor())
            count += 1
        }) {
            Text(text="Change Color (clicked $count times)")
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}