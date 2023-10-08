package ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AlertDialog(isDialogOpen: Boolean,
                onDialogOpenChange: (Boolean)-> Unit,
                title: String,
                message: String,
                onDialogResult: (Boolean)-> Unit,
) {
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog
                // or on the back button
                onDialogOpenChange(false)
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Button(
                    onClick = {  onDialogResult(true) }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {  onDialogResult(false) }) {
                    Text("Cancel")
                }
            }
        )
    }
}