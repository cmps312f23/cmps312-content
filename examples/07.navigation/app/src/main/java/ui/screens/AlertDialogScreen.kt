package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme


@Composable
fun AlertDialogScreen() {
    Column(Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var isDialogOpen by remember { mutableStateOf(false) }
        var dialogResult by remember { mutableStateOf(false) }

        Text("isDialogOpen: $isDialogOpen - Dialog Result: $dialogResult")

        Button(onClick = {
            isDialogOpen = true
        }) {
            Text("Show Dialog Alert")
        }

        ui.components.AlertDialog(isDialogOpen,
            onDialogOpenChange = { isDialogOpen = it },
            title = "Discard draft?",
            message = "This will permanently delete the current e-mail draft.",
            onDialogResult = {
                dialogResult = it
                isDialogOpen = false
            }
        )
    }
}

@Preview
@Composable
fun AlertDialogScreenPreview() {
    AppTheme {
        AlertDialogScreen()
    }
}