package qu.lingosnacks.view.games.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.Score
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.games.components.ProgressIndicator
import qu.lingosnacks.view.theme.YellowLS
import qu.lingosnacks.viewmodel.PackageViewModel

@Composable
fun ScoreScreen(packageViewModel: PackageViewModel, currentUser: User,
                onNavigateToPackage: (String)-> Unit) {
    val scores = packageViewModel.getScores(currentUser.email)
    val scoresSummary = packageViewModel.getScoresSummary(currentUser.email)

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (scores.isEmpty()) {
            Text(
                text = "No scores found",
                color = Color.LightGray,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        } else {
            Surface(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Scores Summary",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(scoresSummary.toList()) {
                            GameTotalScoreCard(it.first, it.second, Modifier.weight(1f))
                        }
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                items(scores) {
                    ScoreCard(it, onNavigateToPackage)
                }
            }
        }
    }
}

@Composable
fun ScoreCard(score: Score, onNavigateToPackage: (String)-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(8.dp)
            .shadow(
                shape = MaterialTheme.shapes.large,
                elevation = 10.dp,
                ambientColor = Color.DarkGray
            ),
        colors = CardDefaults.cardColors(
            containerColor = YellowLS //MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
                .clickable {
                    onNavigateToPackage(score.packageId)
                }) {

                Text(
                    buildAnnotatedString {
                    append("${score.gameName} - Package: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append(score.packageId)
                    }
                    append(" On: ${score.doneOn}")
                },
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            onNavigateToPackage(score.packageId)
                        }
                )
            }
            ProgressIndicator(value = score.score)
        }
    }
}

@Composable
fun GameTotalScoreCard(gameType : String, totalScore: Int, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(color = MaterialTheme.colorScheme.primary, text = "$gameType Game", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        ProgressIndicator(value = totalScore)
    }
}