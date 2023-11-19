package shopapp.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Login
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import shopapp.view.components.displayMessage
import shopapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(onLoginSuccess: ()-> Unit, onSignup: ()-> Unit) {
    val authViewModel =
        viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var email by remember { mutableStateOf("erradi@live.com") }
    var password by remember { mutableStateOf("pass123") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Outlined.Login, contentDescription = "login")
                Text(
                    text = "Sign In",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text( text = "Email" ) },
                placeholder = { Text(text = "Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password") },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
            )
            Button(
                onClick = {
                        authViewModel.signIn(email, password)
                },
                modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
            ) {
                Text(text = "Login")
            }

            if (authViewModel.currentUser != null)
                onLoginSuccess()

            if (authViewModel.errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = authViewModel.errorMessage, style = TextStyle(color = Color.Red))
                displayMessage(message = authViewModel.errorMessage)
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Signup",
                style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable {
                    onSignup()
                }
            )
        }
    }
}
