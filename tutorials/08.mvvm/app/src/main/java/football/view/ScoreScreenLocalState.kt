package football.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.nav.R

@Composable
fun ScoreScreenLocalState() {
    // Ticket from MVVM police => ViewModel (manage state)
    var team1Score by remember { mutableStateOf(0) }
    var team2Score by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .weight(3F)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TeamScore(
                modifier = Modifier.weight(1F),
                iconId = R.drawable.img_rayyan,
                score = team1Score
            ) {
                team1Score++
            }
            TeamScore(
                modifier = Modifier.weight(1F),
                iconId = R.drawable.img_alkhor,
                score = team2Score
            ) {
                team2Score++
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.remaining_time),
            )
            Text(
                textAlign = TextAlign.Center,
                text = "5 mins"
            )
        }
    }

    // Watch the Composable Lifecycle
    // This function is called when the Composable enters the Composition
    LaunchedEffect(Unit) {
        Log.d("LifeCycle->Compose", "onActive ScoreScreen.")
    }

    DisposableEffect(Unit) {
        // onDispose() is called when the Composable leaves the composition
        onDispose {
            Log.d("LifeCycle->Compose", "onDispose ScoreScreen.")
        }
    }
}

@Preview
@Composable
fun ScoreScreenLocalStatePreview() {
    ScoreScreenLocalState()
}