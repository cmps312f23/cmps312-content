package football

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import football.view.LifeCycleObserver
import football.view.theme.AppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //ScoreScreenLocalState()
                    //ScoreScreen()
                    MainScreen()
                }
            }
        }

        // Get current language and screen orientation
        val language = Locale.getDefault().language
        val screenOrientation = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            else -> "Portrait"
        }

        //Watch the activity lifecycle events
        LifeCycleObserver(lifecycle,
            tag = "MainActivity ⚽",
            language, screenOrientation
        )
    }
}