package maps.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun displayMessage(message: String, duration: Int = Toast.LENGTH_LONG) {
    val context = LocalContext.current
    Toast.makeText(context, message, duration).show()
}

fun displayMessage(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}

fun String.removeTrailingComma() =
    this.replace("(?!^),+$".toRegex(), "")