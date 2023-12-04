package qu.lingosnacks.view.auth

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.components.Dropdown
import qu.lingosnacks.viewmodel.AuthViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(authViewModel: AuthViewModel, onSignUp: () -> Unit, onSignIn: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Member") }
    var showErrorMsg by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun isFormReady() =
        firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && photoUrl.isNotBlank()

    fun signUp() {
        if (isFormReady()) {
            val user = User(firstName,
                lastName,
                email,
                password,
                role = "",
                photoUrl)
            authViewModel.signUp(user)
            onSignUp()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(ScrollState(0), true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = {
                firstName = it
            }, label = {
                Text(text = "First Name")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
                keyboardController?.hide()
            })
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                lastName = it
            }, label = {
                Text(text = "Last Name")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
                keyboardController?.hide()
            })
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            }, label = {
                Text(text = "E-Mail")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
                keyboardController?.hide()
            }),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            }, label = {
                Text(text = "Password")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
                keyboardController?.hide()
            }),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Dropdown(
            label = "Role",
            options = authViewModel.getRoles(),
            selectedOption = role,
            onSelectionChange = { role = it })

        OutlinedTextField(
            value = photoUrl,
            onValueChange = {
                photoUrl = it
            }, label = {
                Text(text = "Photo Url")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
                signUp()
            }),
            singleLine = true
        )

        if (showErrorMsg)
            Text(
                text = "Unable to create account",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(10.dp)
            )

        //Sign up button
        Button(
            onClick = {
                signUp()
            },
            enabled = isFormReady()
        ) {
            Text(text = "Sign In")

        }

        Text(
            buildAnnotatedString {
                append("Already have an account? ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                    append("Sign In")
                }
            },
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onSignIn()
                }
        )
    }
}