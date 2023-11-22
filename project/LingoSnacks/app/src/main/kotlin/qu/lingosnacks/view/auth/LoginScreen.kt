package qu.lingosnacks.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import qu.lingosnacks.R
import qu.lingosnacks.viewmodel.AuthViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(authViewModel: AuthViewModel, onSignIn: () -> Unit, onSignUp: () -> Unit) {
    var email by remember { mutableStateOf("a1@test.com") }
    var password by remember { mutableStateOf("pass123") }
    var showErrorMsg by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun isFormReady() = email.isNotBlank() && password.isNotBlank()

    fun signIn() {
        if (isFormReady()) {
            val user = authViewModel.signIn(email, password)
            if (user != null)
                onSignIn()
            else
                showErrorMsg = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.largelogo),
                contentDescription = "LingoSnacks Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    showErrorMsg = false
                }, label = {
                    Text(text = "Email")
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
                    showErrorMsg = false
                }, label = {
                    Text(text = "Password")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    signIn()
                }),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (showErrorMsg) Text(
                text = "Invalid email or password",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(10.dp)
            )
        }

        //Sign in button
        Button(
            onClick = {
                signIn()
            },
            enabled = isFormReady()
        ) {
            Text(text = "Sign In")
        }

        //Don't have an account? Sign up text with button to sign up
        Text(buildAnnotatedString {
            append("Don't have an account? ")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                append("Sign Up")
            }
        },
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onSignUp()
                }
        )
    }
}