package compose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import compose.ui.components.*
import compose.ui.layout.ResponsiveScreen
import ui.theme.AppTheme
import compose.ui.layout.ArtistCardScreen
import compose.ui.layout.BoxLayoutScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the ui.theme
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
    var screenTitle by remember { mutableStateOf("Compose UI") }

    Scaffold(
        topBar = {
            TopBar(onRouteChange= {
                screenTitle = it.title
                navController.navigate(it.route)
            }, screenTitle)
        },
        //bottomBar = { BottomNavigationBar(navController) }
    ) {
        AppNavigator(navController = navController)
    }
}


@Composable
fun AppNavigator(navController: NavHostController) {
    val uri = "https://cmp312.qu.edu.qa"
    NavHost(navController, startDestination = Screen.Button.route) {
        composable(Screen.TextField.route) {
            TextFieldScreen()
        }

        composable(Screen.Button.route) {
           ButtonScreen()
        }

        composable(Screen.RadioButton.route) {
            RadioButtonScreen()
        }

        composable(Screen.Switch.route) {
            SwitchScreen()
        }
        composable(Screen.CheckBox.route) {
            CheckBoxScreen()
        }
        composable(Screen.Dropdown.route) {
            DropdownScreen()
        }
        composable(Screen.Slider.route) {
            SliderScreen()
        }

        composable(Screen.Card.route) {
            ArtistCardScreen()
        }

        composable(Screen.Box.route) {
            BoxLayoutScreen()
        }

        composable(Screen.Responsive.route) {
            ResponsiveScreen()
        }

        composable(Screen.TipCalculator.route) {
            TipCalculator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onRouteChange: (Screen) -> Unit, screenTitle: String = "Compose UI") {
   TopAppBar(
        title = { Text(screenTitle)},
        actions = {
            IconButton(onClick = { onRouteChange(Screen.TipCalculator) }) {
                Icon(imageVector = Icons.Outlined.AccountBox, contentDescription = "Tip Calculator")
            }
            TopBarMenu(onRouteChange)
        }
   )
}

@Composable
fun TopBarMenu(onRouteChange: (Screen) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        Screen.TextField,
        Screen.Button,
        Screen.RadioButton,
        Screen.Switch,
        Screen.CheckBox,
        Screen.Dropdown,
        Screen.Slider,
        Screen.Divider,
        Screen.Card,
        Screen.Box,
        Screen.Responsive,
        Screen.Divider,
        Screen.TipCalculator
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
                        onRouteChange(menuItem)
                    }, text = {
                        Text(menuItem.title)
                    })
                }
            }
        }
    }
}
