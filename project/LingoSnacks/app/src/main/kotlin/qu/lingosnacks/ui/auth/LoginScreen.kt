package qu.lingosnacks.ui.auth

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qu.lingosnacks.R
import qu.lingosnacks.repository.UserRepository
import qu.lingosnacks.ui.navigation.NavDestination
import qu.lingosnacks.ui.theme.AppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navigateTo: (String) -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun requiredFilled() = email.isNotBlank() && password.isNotBlank()

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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    if (requiredFilled()) {
                        (!UserRepository.loginBool(email, password)).also { error = it }
                        if (!error) {
                            email = ""
                            password = ""
                            navigateTo(NavDestination.PackagesScreen.route)
                        }
                    }
                }),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (error) Text(
                text = "Wrong email or password",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(10.dp)
            )
        }

        //Sign in button
        Button(
            onClick = {
                (!UserRepository.loginBool(email, password)).also { error = it }
                if (!error) {
                    email = ""
                    password = ""
                    navigateTo(NavDestination.PackagesScreen.route)
                }
            },
            enabled = requiredFilled()
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
                    email = ""
                    password = ""
                    navigateTo(NavDestination.SignupScreen.route)
                }
        )

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        Surface {
//            LoginScreen()
        }
    }
}