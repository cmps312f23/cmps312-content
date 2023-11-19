package shopapp.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import shopapp.entity.User
import shopapp.view.components.TopBarWithSave
import shopapp.view.components.Dropdown
import shopapp.view.components.displayMessage
import shopapp.viewmodel.AuthViewModel

enum class Role(val label: String) {
    ADMIN("Admin"),
    EMPLOYEE("Employee")
}

@Composable
fun SignupScreen(onNavigateBack: ()->Unit) {
    val authViewModel =
        viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val context = LocalContext.current
    var screenTitle = "Signup"

    val roles = Role.values().map { it.label }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    fun onSubmit() {
        val user = User(
            uid = "",
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            role = role
        )
        try {
            authViewModel.signUp(user)
            onNavigateBack()
        } catch (e: Exception) {
            displayMessage(context, message = e.message ?: "Signup failed")
        }
    }

    Scaffold(
        topBar = {
            TopBarWithSave(
                title = screenTitle,
                onNavigateBack = onNavigateBack,
                onSubmit = { onSubmit() })
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Dropdown(label = "Role",
                options = roles,
                selectedOption = role,
                onSelectionChange = { role = it },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
        }
    }
}