package compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.ui.R
import compose.ui.ui.theme.AppTheme

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