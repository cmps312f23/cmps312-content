package ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(coroutineScope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(
        title = {
            Text(text = "Navigation")
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            IconButton(onClick = {
                // Open the drawer while avoiding blocking to UI
                // This is will be studied in depth later
                coroutineScope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}