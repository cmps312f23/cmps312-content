package compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldScreen(){
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    Column(
        Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PasswordTextField(password = password, onPasswordChange = { password = it })
        PhoneTextField(phone = phone, onPhoneChange = { phone = it })
        Text(text = "Phone $phone - Password: $password")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneTextField(phone: String, onPhoneChange: (String) -> Unit) {
    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChange,
        label = { Text("Mobile number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

@Preview
@Composable
fun TextFieldScreenPreview(){
    TextFieldScreen()
}