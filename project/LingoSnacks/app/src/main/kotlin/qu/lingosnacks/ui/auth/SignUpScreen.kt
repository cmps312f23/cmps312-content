package qu.lingosnacks.ui.auth

import androidx.compose.foundation.Image
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
fun SignupScreen(navigateTo: (String) -> Unit) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    fun requiredFilled() =
        firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(ScrollState(0), true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "LingoSnacks Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )

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
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
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
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
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
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
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
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
            }),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            }, label = {
                Text(text = "Confirm Password")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                fM.clearFocus()
                kB?.hide()
                if (requiredFilled()) {
                    (!UserRepository.signUp(
                        firstName,
                        lastName,
                        email,
                        password,
                        confirmPassword
                    )).also { error = it }
                    if (!error) {
                        firstName = ""
                        lastName = ""
                        email = ""
                        password = ""
                        confirmPassword = ""
                        navigateTo(NavDestination.PackagesScreen.route)
                    }
                }
            }),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        if (error)
            Text(
                text = "Unable to create account",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(10.dp)
            )

        //Sign up button
        Button(
            onClick = {
                (!UserRepository.signUp(
                    firstName,
                    lastName,
                    email,
                    password,
                    confirmPassword
                )).also { error = it }
                if (!error) {
                    firstName = ""
                    lastName = ""
                    email = ""
                    password = ""
                    confirmPassword = ""
                    navigateTo(NavDestination.PackagesScreen.route)
                }
            },
            enabled = requiredFilled()
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
                    firstName = ""
                    lastName = ""
                    email = ""
                    password = ""
                    confirmPassword = ""
                    navigateTo(NavDestination.LoginScreen.route)
                }
        )
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    AppTheme {
        Surface {
//            SignupScreen()
        }
    }
}