package qu.lingosnacks.ui.editor.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.ui.theme.CyanLS

@Composable
fun DeleteDialog(
    learningPackage: LearningPackage,
    onCancelClicked: () -> Unit,
    onConfirmClicked: (LearningPackage) -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onCancelClicked() },
        confirmButton = {
            Button(
                onClick = {
                    Toast.makeText(context, "Package has been deleted", Toast.LENGTH_SHORT)
                        .show()
                    onConfirmClicked(learningPackage)
                },
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
            ) {
                Text("Confirm", modifier = Modifier.padding(horizontal = 2.5.dp))
            }
        },
        dismissButton = {
            Button(
                onClick = { onCancelClicked() },
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, contentColor = CyanLS
                ),
                border = BorderStroke(1.dp, CyanLS),
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text("Cancel", modifier = Modifier.padding(horizontal = 2.5.dp))
            }
        },
        title = { Text("Delete") },
        text = { Text("Are you sure you want to delete package \"${learningPackage.title}\"?") }
    )
}