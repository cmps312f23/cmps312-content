package ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Other common intent examples available @ https://developer.android.com/guide/components/intents-common.html
@Composable
fun ExternalAppScreen() {
    val quLatitude = "25.3773"
    val quLongitude = "51.4912"
    val context = LocalContext.current
    Column(Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                openMap(context, latitude = quLatitude, longitude = quLongitude)
            }) {
            Text(
                text = "Open QU map"
            )
        }

        Button(
            onClick = {
                openUri(context, uri = "https://www.qu.edu.qa")
            }) {
            Text(
                text = "Open QU website"
            )
        }

        Button(
            onClick = {
                sendEmail(
                    context,
                    toEmail = "compose@test.com",
                    subject = "Compose is cool!",
                    message = "Join us"
                )
            }) {
            Text(
                text = "Send an email"
            )
        }

        Button(
            onClick = {
                shareContent(
                    context, content = "Jetpack Compose is cool!"
                )
            }) {
            Text(
                text = "Share Content"
            )
        }

        Button(
            onClick = {
                dialPhoneNumber(
                    context, "111"
                )
            }) {
            Text(
                text = "Call Ooredoo"
            )
        }
    }
}

fun openMap(context: Context, latitude: String, longitude: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:$latitude, $longitude")
    )
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

fun openUri(context: Context, uri: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(uri)
    )
    context.startActivity(intent)
}

fun sendEmail(context: Context, toEmail: String, subject: String, message: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(toEmail))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(intent, "Choose an Email client"))
}

fun shareContent(context: Context, content: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}

@Preview
@Composable
fun ExternalAppScreenPreview() {
    ExternalAppScreen()
}