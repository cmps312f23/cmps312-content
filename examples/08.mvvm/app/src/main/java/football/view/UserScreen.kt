package football.view


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import football.viewmodel.UserViewModel

@Composable
fun UserScreen(userViewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }

    Column (verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text ("Username")
                }
            )
            Button(modifier = Modifier.padding(top = 16.dp),
                   onClick = {
                       userViewModel.addUser(username)
                       username = ""
                   }
            ) {
                Text("Add")
            }
        }
        Text("Users:")
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            for (user in userViewModel.users) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = user, modifier  = Modifier.weight(2F))
                    Button(
                        modifier  = Modifier.weight(1F),
                        onClick = { userViewModel.onAuthChange(user) }
                    ) {
                        Text("Switch User")
                    }
                }
            }
        }
    }

    // Watch the Composable Lifecycle
    // This function is called when the Composable enters the Composition
    LaunchedEffect(Unit) {
        Log.d("LifeCycle->Compose", "onActive UserScreen.")
    }
    DisposableEffect(Unit) {
        // onDispose() is called when the Composable leaves the composition
        onDispose {
            Log.d("LifeCycle->Compose", "onDispose UserScreen.")
        }
    }
}