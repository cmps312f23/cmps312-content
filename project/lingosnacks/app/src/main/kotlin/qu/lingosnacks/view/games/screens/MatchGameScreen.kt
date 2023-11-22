package qu.lingosnacks.view.games.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import qu.lingosnacks.entity.Definition
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Score
import qu.lingosnacks.entity.User
import qu.lingosnacks.utils.getRandomId
import qu.lingosnacks.utils.todayDate
import qu.lingosnacks.view.games.components.GameType
import qu.lingosnacks.view.games.components.DragTarget
import qu.lingosnacks.view.games.components.DropTarget
import qu.lingosnacks.view.games.components.LongPressDraggable
import qu.lingosnacks.view.games.popup.MatchScoreGamePopup
import qu.lingosnacks.viewmodel.PackageViewModel

data class WordDefinition(
    val id: Int,
    val word: String,
    val definition: String,
    var matched: Boolean = false
)

@Composable
fun MatchGameScreen(learningPackage: LearningPackage,
                    currentUser: User,
                    packageViewModel: PackageViewModel,
                    onNavigateBack: () -> Unit) {

    var isGameOver by remember {
        mutableStateOf(false)
    }

    val wordDefinitionList = remember {
        mutableStateListOf<WordDefinition>()
    }

    //we pass this as shuffled
    val shuffledWordDefinitionList = remember {
        mutableStateListOf<WordDefinition>()
    }
    var matchCount by remember { //keep count of how many is matched
        mutableIntStateOf(0)
    }

    var attemptsCount by remember {
        mutableIntStateOf(0)
    }

    var timeLeft by remember { mutableIntStateOf(60) }
    var countdownRunning by remember { mutableStateOf(true) } // to control timer

    fun initGame() {
        val words = learningPackage.words
        val definitions = learningPackage.words.flatMap {
            it.definitions.shuffled().firstOrNull()?.let { firstDefinition ->
                listOf(
                    Definition(
                        text = firstDefinition.text,
                        source = firstDefinition.source
                    )
                )
            } ?: emptyList()
        }

        // Zip the words and definitions to create WordAndDefinition objects
        val wordDefinitions = words.zip(definitions).mapIndexed { index, (word, definition) ->
            WordDefinition(id = index + 1, word = word.text, definition = definition.text)
        }

        wordDefinitionList.clear()
        wordDefinitionList.addAll(wordDefinitions)
        println("wordDefinitionList $wordDefinitionList")

        shuffledWordDefinitionList.clear()
        shuffledWordDefinitionList.addAll(wordDefinitionList.shuffled().toMutableStateList())

        matchCount = 0
        attemptsCount = 0
        timeLeft = 60
        countdownRunning = true
    }

    fun addScore() {
        val score =
            ((matchCount.toDouble() / shuffledWordDefinitionList.size.toDouble()) * 100).toInt()
        val scoreObj = Score(
            scoreId = getRandomId(),
            packageId = learningPackage.packageId,
            uid = currentUser.uid,
            gameName = GameType.Match.toString(),
            score = score,
            outOf = 100,
            doneOn = todayDate()
        )
        packageViewModel.addScore(scoreObj)
    }

    LaunchedEffect(Unit) {
        // Actions to perform when LaunchedEffect enters the Composition
        initGame()
    }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0 && countdownRunning) {
            delay(1000L)
            timeLeft--

        }
    }

    LongPressDraggable {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        )
                    ) {
                        append("Match Word to Definition")
                    }
                },
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                WordLists(shuffledWordDefinitionList)

                //displays the definitions
                DefinitionList(wordDefinitionList, shuffledWordDefinitionList,
                    onDrop = {
                        attemptsCount++
                        var index = wordDefinitionList.indexOfFirst { p -> p.id == it }
                        println("definition: index $index")
                        if (index >= 0) {
                            wordDefinitionList[index] =
                                wordDefinitionList[index].copy(matched = true)
                        }

                        index = shuffledWordDefinitionList.indexOfFirst { p -> p.id == it }
                        println("shuffledWords: index $index")

                        if (index >= 0) {
                            if (!shuffledWordDefinitionList[index].matched) {
                                //we use another if statement to check if the word at the specified index is not already matched
                                // If it's not matched, we update the shuffledWordDefinitionList and increment matchCount
                                shuffledWordDefinitionList[index] =
                                    shuffledWordDefinitionList[index].copy(matched = true)
                                matchCount++ //increment the words matched correctly
                            }
                        }
                    })
                println("current matchCount= $matchCount")


            }

            //contains the timer and the number of correct matches
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Time left⏳: $timeLeft",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Text(
                    text = "Attempts: $attemptsCount - Matches: ${matchCount}/${shuffledWordDefinitionList.size}",
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    //call the dialog to either exit or retry again
    if (matchCount >= shuffledWordDefinitionList.size || timeLeft == 0) {
        countdownRunning = false //stops timer
        MatchScoreGamePopup(matchCount,
            shuffledWordDefinitionList.size,
            attemptsCount,
            "Game over!",
            onPlayAgain = {
                addScore()
                initGame()
            },
            onEndPlay = {
                addScore()
                isGameOver = true
                onNavigateBack()
            })
    }
}

//this is done don't touch it!!
@Composable
fun WordLists(words: List<WordDefinition>) {
//    println("this wordlist: " + learningPackage.words)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.4f)
//            .fillMaxHeight(0.9f)
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(words) {

            if (!it.matched) { //if false, it is not matched
                DragTarget(dataToDrop = it, modifier = Modifier.padding(6.dp)) {
                    SuggestionChip(onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.medium,
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        border = SuggestionChipDefaults.suggestionChipBorder(
                            borderWidth = 2.dp,
                        ),
                        label = {
                            Text(
                                text = it.word,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                ),
                                textAlign = TextAlign.Center
                            )
                        })
                }
            }
        }
    }
}

@Composable
fun DefinitionList(
    wordDefinition: List<WordDefinition>,
    shuffledWordDefinition: SnapshotStateList<WordDefinition>,
    onDrop: (wordIndex: Int) -> Unit,
    ) {

    println("Definitions: $wordDefinition")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {

        //i will reshuffle it everytime its solved
        items(shuffledWordDefinition.shuffled()) {
            DropTarget<WordDefinition>(
                modifier = Modifier.padding(6.dp)
            ) { isInBound, definition ->
                val bgColor = if (isInBound) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.background
                }

                println("Definition: $definition")

                if (definition != null) {
                    if (isInBound && definition.id == it.id) {
                        onDrop(it.id)
                        /*println("Pet: isInBound $isInBound")
                        println("Pet: $pet")
                        println("Pet: it $it")*/
                    }
                }
                //calls each cards
                DefinitionCard(it, bgColor) //change color dynamic later
            }//end of dropTarget
        }
    }
}

@Composable
fun DefinitionCard(wordDefinition: WordDefinition, bgColor: Color) {
    //change this Column to a row later
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(bgColor, RoundedCornerShape(16.dp))
            .padding(5.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${wordDefinition.definition}.",
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        )

        // Here is to see if the word matches the definition
        if (wordDefinition.matched) {
            Text(
                text = wordDefinition.word, //here display the word instead
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}