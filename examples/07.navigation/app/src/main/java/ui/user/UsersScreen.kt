package ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ui.screens.displayMessage
import ui.components.Dropdown
import ui.user.User
import ui.user.UserViewModel

@Composable
fun UsersScreen(onNavigateToDetails: (Int) -> Unit) {
    /* Get an instance of the shared viewModel
    Make the activity the store owner of the viewModel
    to ensure that the same viewModel instance is used for all screens */
    val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var selectedUserId by remember {
        mutableStateOf(0)
    }

    var selectedUserName by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = { FloatingButton() }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = MaterialTheme.colorScheme.primaryContainer
            )
            // .associate Converts a list to a map
            val options =
                userViewModel.users.associate { Pair(it.userId, "${it.userId} ${it.name}") }
            Text(text = "Users count ${userViewModel.usersCount}")
            Dropdown(
                label = "Select a User",
                options = options,
                selectedOption = (selectedUserId to selectedUserName),
                onSelectionChange = { (userId, userName) ->
                    run {
                        selectedUserId = userId
                        selectedUserName = userName
                    }
                })

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {
                onNavigateToDetails(selectedUserId)
            }) {
                Text("Profile Details")
            }
        }
    }
}

@Composable
fun FloatingButton() {
    /* Get an instance of the shared viewModel
       Make the activity the store owner of the viewModel
       to ensure that the same viewModel instance is used for all destinations
    */
    //val users = UserViewModel()

    val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    FloatingActionButton(
        //backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = {
            userViewModel.addUser(User(0, "FN", "Added by FAB", "test@test.com"))
            displayMessage(context, "A new user added. Users count ${userViewModel.usersCount}")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White
        )
    }
}