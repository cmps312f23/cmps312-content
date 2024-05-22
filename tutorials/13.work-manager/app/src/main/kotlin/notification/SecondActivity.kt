package notification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import football.view.theme.AppTheme


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Receiving data in the second activity
                    val intent = intent
                    val receivedData = intent.getStringExtra("GREETING").orEmpty()
                    Text(text = receivedData)
                }
            }
        }
    }
}
