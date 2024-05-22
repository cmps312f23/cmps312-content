package compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.compose.R
import ui.theme.AppTheme

@Composable
fun ButtonScreen() {
    var message by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = message)
        Button(onClick = { message = "Button clicked" }) {
            Text("Button")
        }

        OutlinedButton(onClick = { message = "OutlinedButton clicked" }) {
            Text("OutlinedButton")
        }

        TextButton(onClick = { message = "TextButton clicked" }) {
            Text("TextButton")
        }

        // Search for icons @ https://fonts.google.com/icons
        IconButton(onClick = { message = "Search IconButton clicked" }) {
            Icon(
                Icons.Outlined.List,
                contentDescription = "Search",
            )
        }

        IconButton(onClick = { message = "Quran IconButton clicked" }) {
            Icon(painterResource(id = R.drawable.ic_quran), "Quran")
        }
    }
}

@Preview
@Composable
fun ButtonScreenPreview() {
    AppTheme {
        ButtonScreen()
    }
}