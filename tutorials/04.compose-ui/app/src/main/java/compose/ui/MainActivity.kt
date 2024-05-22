package compose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.ui.state.ClicksCounterScreen
import compose.ui.basics.ComposeLogoScreen
import compose.ui.basics.HelloScreen
import compose.ui.modifier.clickable.ClickableTextScreen
import compose.ui.modifier.styling.StylingScreen
import compose.ui.state.WelcomeScreen
import compose.ui.ui.theme.ComposeUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUITheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(onRouteChange = {
                navController.navigate(it)
            })
        },
        //bottomBar = { BottomNavigationBar(navController) }
    ) {
        AppNavigator(navController = navController)
    }
}


@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.ComposeLogo.route) {
        composable(Screen.Hello.route) {
            HelloScreen()
        }
        composable(Screen.ClicksCounter.route) {
            ClicksCounterScreen()
        }
        composable(Screen.ComposeLogo.route) {
            ComposeLogoScreen()
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen()
        }
        composable(Screen.Clickable.route) {
            ClickableTextScreen()
        }

        composable(Screen.Styling.route) {
            StylingScreen()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onRouteChange: (String) -> Unit) {
    TopAppBar(
        title = { Text("Compose UI") },
        actions = {
            TopBarMenu(onRouteChange)
        }
    )
}

@Composable
fun TopBarMenu(onRouteChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        Screen.Hello,
        Screen.ComposeLogo,
        Screen.Divider,
        Screen.ClicksCounter,
        Screen.Welcome,
        Screen.Divider,
        Screen.Styling,
        Screen.Clickable
    )

    Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More..."
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            menuItems.forEach { menuItem ->
                if (menuItem.title == "Divider") {
                    Divider()
                } else {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onRouteChange(menuItem.route)
                    },
                        text = { Text(menuItem.title) }
                    )
                }
            }
        }
    }
}
