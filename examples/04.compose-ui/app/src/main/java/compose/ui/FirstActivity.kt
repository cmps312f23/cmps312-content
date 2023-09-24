package compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import compose.ui.state.ClickCounter
import compose.ui.state.ClicksCounterScreen
import compose.ui.state.WelcomeScreen
import compose.ui.ui.theme.ComposeUITheme

class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeScreen()
            //ClicksCounterScreen()
            //HelloWorld(name = "CMPS 312 Team")

/*            ComposeUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }*/
        }
    }
}

@Composable
fun HelloWorld(name: String) =
    Text("Hello $name from Compose!")


@Preview(showBackground = true)
@Composable
fun HelloWorldPreview() {
    HelloWorld("CMPS 312 Team")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeUITheme {
        Greeting("Android")
    }
}