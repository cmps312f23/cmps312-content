package qu.lingosnacks.view.games.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MatchScoreGamePopup(
    matchedCount:Int,
    wordsCount:Int,
    attemptsCount: Int,
    title:String,
    onEndPlay: () -> Unit,
    onPlayAgain: () -> Unit
) {
    val attemptsRatio = (if (attemptsCount == 0) 0 else matchedCount / attemptsCount) * 100
    val score = ((matchedCount.toDouble() / wordsCount.toDouble()) * attemptsRatio).toInt()
    println("points: $score - matchedCount: $matchedCount - wordsCount: $wordsCount")
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Score: $score",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer, // Change the color to the one you prefer
                        )
                    )

                    Text(
                        text = "Matched: ${matchedCount}/${wordsCount}",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Text(
                        text = "Attempts: ${attemptsCount}/${wordsCount}",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    //call the Matching Game Screen again (play again)
                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            onPlayAgain()
                        }) {
                        Text(
                            text = "Play Again",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = {
                            onEndPlay()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Return to Main Menu",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PopupMatchingGamePreview() {
    MatchScoreGamePopup(1, 10, 4, "Game Lost", onEndPlay = {}, onPlayAgain = {})
}