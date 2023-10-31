package football.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.nav.R
import football.view.theme.AppTheme
import football.viewmodel.ScoreViewModel

@Composable
fun ScoreScreen(scoreViewModel : ScoreViewModel) {
    // Get an instance of the ScoreViewModel
    /*
    var team1Score by remember {
        mutableStateOf(0)
    }

    var team2Score by remember {
        mutableStateOf(0)
    }*/

    //collectAsStateWithLifecycle() collects values from a Flow in a lifecycle-aware
    // manner, allowing the app to save unneeded app resources.
    // It represents the latest emitted value as a Compose State.
    val newsUpdate = scoreViewModel.newsFlow.collectAsStateWithLifecycle("")
    val timeRemaining = scoreViewModel.timeRemainingFlow.collectAsStateWithLifecycle("")

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
                score = scoreViewModel.team1Score
            ) { //team1Score) {
                scoreViewModel.onIncrementTeam1Score()
            }
            TeamScore(
                modifier = Modifier.weight(1F),
                iconId = R.drawable.img_alkhor,
                score = scoreViewModel.team2Score
            ) { //team2Score) {
                scoreViewModel.onIncrementTeam2Score()
                //team2Score++
            }
        }
        Box(
            Modifier.weight(1F),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Recomposes whenever timeRemaining changes
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.remaining_time),
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = timeRemaining.value,
                    )
                }
                // Recomposes whenever newsUpdate changes
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    text = "\uD83D\uDCE2 ${newsUpdate.value}",
                )
            }
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

@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview() {
    AppTheme {
        val scoreViewModel = viewModel<ScoreViewModel>()
        ScoreScreen(scoreViewModel)
    }
}