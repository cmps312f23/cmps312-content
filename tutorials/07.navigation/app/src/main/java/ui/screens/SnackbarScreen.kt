package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme

@Composable
fun SnackbarScreen() {
    Column {
        val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }

        Button(onClick = { setSnackBarState(!snackbarVisibleState) }) {
            if (snackbarVisibleState) {
                Text("Hide Snackbar")
            } else {
                Text("Show Snackbar")
            }
        }
        if (snackbarVisibleState) {
            Snackbar(
                action = {
                    Button(onClick = { setSnackBarState (false)}) {
                        Text("x")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) { Text(text = "This is a snackbar!") }
        }
    }
}

@Preview
@Composable
fun SnackbarScreenPreview() {
    AppTheme {
        SnackbarScreen()
    }
}