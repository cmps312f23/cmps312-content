package qu.lingosnacks.view.games.unscramble

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import qu.lingosnacks.entity.Score
import qu.lingosnacks.entity.User
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.utils.getRandomId
import qu.lingosnacks.utils.todayDate
import qu.lingosnacks.view.components.DialogBox
import qu.lingosnacks.view.games.components.DragTarget
import qu.lingosnacks.view.games.components.DropTarget
import qu.lingosnacks.view.games.components.GameType
import qu.lingosnacks.view.games.components.LongPressDraggable
import qu.lingosnacks.view.theme.LavendarMist
import qu.lingosnacks.view.theme.LightSlateBlue
import qu.lingosnacks.view.theme.PalePurple
import qu.lingosnacks.view.theme.Tuna
import qu.lingosnacks.viewmodel.PackageViewModel

data class Word(var id: Int, var text: String, var matched: Boolean = false)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnscrambleSentenceForm(
    sentence: String,
    currentUser: User,
    packageId: String,
    packageViewModel: PackageViewModel,
) {
    val context = LocalContext.current

    val originalSentence = sentence.split(" ")
        .mapIndexed { index, wordText ->
            Word(text = wordText, id = index + 1)
        }

    val originalSentenceList = remember {
        mutableStateListOf(*originalSentence.toTypedArray())
    }
    val shuffledSentence = remember {
        originalSentenceList.shuffled().toMutableStateList()
    }

    var resetTrigger by remember { mutableIntStateOf(0) }
    var attemptsCount by remember {
        mutableIntStateOf(0)
    }
    var matchCount by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val maxScore = originalSentence.size

    fun addScore() : Int {
        val attemptsRatio = (if (attemptsCount == 0) 0 else matchCount / attemptsCount) * 100
        val score = (matchCount.toDouble()/maxScore.toDouble() * attemptsRatio).toInt()
        val scoreObj = Score(
            scoreId = getRandomId(),
            packageId = packageId,
            uid = currentUser.uid,
            gameName = GameType.Unscramble.toString(),
            score = score,
            outOf = 100,
            doneOn = todayDate()
        )
        packageViewModel.addScore(scoreObj)
        return score
    }

    LaunchedEffect(matchCount) {
        if (matchCount == originalSentenceList.size) {
            val score = addScore()
            displayMessage(context,"Congratulations! Your score is: $score")
            //showDialog = true
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Attempts: $attemptsCount - Matches: $matchCount/$maxScore",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Tuna,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .border(
                            BorderStroke(2.dp, PalePurple),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            color = LavendarMist,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 8.dp)
                )
            }
        }
        item {
            LongPressDraggable(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShuffledWordsList(shuffledSentence)
                    WordsList(
                      originalSentenceList,
                      onDrop = {
                        var index = originalSentenceList.indexOfFirst { wrd ->
                            wrd.id == it.id
                        }

                        if (index >= 0) {
                            originalSentenceList[index] =
                                originalSentenceList[index].copy(matched = true)
                        }

                        index = shuffledSentence.indexOfFirst { wrd ->
                            wrd.id == it.id
                        }

                        if (index >= 0) {
                            shuffledSentence.remove(shuffledSentence[index])
                        }
                        if (matchCount < originalSentenceList.size) {
                            matchCount++
                        }
                        attemptsCount++
                    })

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Tuna,
                            contentColor = Color.White
                        ),
                        onClick = {
                            val shuffled = originalSentenceList.shuffled()
                            for (idx in 0 until originalSentenceList.size) {
                                originalSentenceList[idx] = originalSentenceList[idx].copy(matched = false)
                                val shuffledWord = shuffledSentence.find{it.id == shuffled[idx].id}
                                if(shuffledWord == null) shuffledSentence.add(shuffled[idx])
                            }
                            resetTrigger++
                            matchCount = 0
                            attemptsCount = 0
                        },
                    ) {
                        Text(text = "Reset")
                    }
                }
            }
        }


    }
    if (showDialog) {
        DialogBox(
            onDismissRequest = { showDialog = false },
            message = "Congratulations! Your score is: $matchCount",
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShuffledWordsList(
    shuffledSentence: SnapshotStateList<Word>,
) {
    val movableItems =
        shuffledSentence.map { word ->
            movableContentOf {
                DragTarget(dataToDrop = word, modifier = Modifier.padding(6.dp)) {
                    SuggestionChip(onClick = { },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = LightSlateBlue
                        ),
                        label = {
                            Text(
                                text = word.text,
                                fontSize = 24.sp,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    )
                }
            }
        }

    FlowRow(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 25.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        shuffledSentence.forEachIndexed { index, _ ->
            movableItems[index]()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordsList(
    words: List<Word>,
    onDrop: (word: Word) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(words.size) {
            DropTarget<Word>(
                modifier = Modifier.padding(6.dp)
            ) { isInBound, word ->
                val bgColor = if (isInBound) {
                    Color.Yellow
                } else {
                    Color.White
                }
                if (word != null) {
                    if (isInBound && word.id == words[it].id) {
                        onDrop(words[it])
                    }
                }
                WordCard(
                    word = words[it],
                    bgColor = bgColor,
                    index = it,
                )
            }
        }
    }
}

@Composable
fun WordCard(
    word: Word, bgColor: Color, index: Int
) {
    Column(
        modifier = Modifier
            .size(90.dp, 70.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(bgColor, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${index + 1}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        if (word.matched) {
            Text(
                text = word.text,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}