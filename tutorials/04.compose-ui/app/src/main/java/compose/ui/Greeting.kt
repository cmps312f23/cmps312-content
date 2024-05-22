package compose.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameEditor(name: String, onNameChange: (String) -> Unit) {
    //@DoNotReinit

    Row {
        OutlinedTextField(value = name, onValueChange = {
            newName -> onNameChange(newName)
        })

        Text(text = name)
    }
}

@Composable
fun Greeting(name: String) = Text("Salam to $name from Compose")

@Composable
fun GreetingScreen() {
    var name by remember { mutableStateOf("Team") }
    Column {
        val editor = NameEditor(name, onNameChange = { newName -> name = newName })
        Greeting(name = name)
    }
}

@Preview
@Composable
fun PreviewGreeting() = GreetingScreen()