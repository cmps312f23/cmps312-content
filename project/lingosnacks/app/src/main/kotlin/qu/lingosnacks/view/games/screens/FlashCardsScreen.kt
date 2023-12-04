package qu.lingosnacks.view.games.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Word
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.view.games.components.FlashCard
import qu.lingosnacks.view.games.components.HorizontalCarousel
import qu.lingosnacks.view.packages.CustomDivider

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun FlashCardsScreen(learningPackage: LearningPackage) {
    val words = learningPackage.words
    // Pager state helps keep track of current pages, handle swipe gestures, handle page indicators, etc...
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        // provide pageCount
        learningPackage.words.size
    }

    // The below scope is used for animating/moving between two pages
    val scope = rememberCoroutineScope()

    var currentWordIndex by remember {
        mutableIntStateOf(0)
    }

    var currentWord by remember {
        mutableStateOf(words[currentWordIndex])
    }

    var flashCardContentId by remember {
        mutableStateOf( "wordPage" )
    }

    displayMessage(message = "Click the card to flip it")
    
    // A Box to contain the flash cards, the definitions and sentences buttons, and the words row list
    Scaffold(bottomBar = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            // The word list tab bar
            WordsTabBar(
                currentPageIndex = currentWordIndex,
                pagerState = pagerState,
                scope = scope,
                words = words,
                onUpdateCardContent = {
                    // if the word tab is clicked, change the page content back to display the Word
                    flashCardContentId = it
                }
            )
        }
    }) { paddingValues ->
        Card(
            modifier = Modifier.padding(paddingValues),
            colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Tab pages: they contain the pages that the user can swipe through the words list
                FlashCard(
                    pageCount = words.size,
                    pagerState = pagerState,
                    onSentencesClicked = { if(it) flashCardContentId = "sentences" },
                    onUpdateCurrentWord = {
                        currentWordIndex =it
                        currentWord = words[currentWordIndex]
                    } )
                {
                    // AnimatedContent: transitions/animates between the definitions and sentences
                    AnimatedContent(targetState = flashCardContentId, label = "Flash Card Content")
                    { cardContentId ->
                        when (cardContentId.lowercase()) {
                            // if the page id is word item, display the card with the word inside it as title
                            "wordpage" -> {
                                WordPage(word = currentWord, onDisplay = {
                                    if(it) flashCardContentId = "sentences" }) {
                                    flashCardContentId = it
                                }
                            }
                            // if the page id is definitions page, display the definitions
                            "definitions" -> {
                                DefinitionsPage(word = currentWord,
                                    onDisplay = {
                                        if (it) flashCardContentId = "sentences"
                                    }) {
                                    flashCardContentId = it
                                }
                            }
                            // if the page id is sentences page, display the sentences
                            "sentences" -> {
                                SentencesPage(word = currentWord) {
                                    flashCardContentId = it
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordsTabBar(currentPageIndex : Int, pagerState: PagerState,
                scope : CoroutineScope, words : List<Word>,
                onUpdateCardContent : (String) -> Unit) {
    ScrollableTabRow(
        modifier = Modifier,
        selectedTabIndex = currentPageIndex,
        edgePadding = 5.dp,
    ) {
        words.map { it.text }.forEachIndexed { index, word ->
            Tab(
                modifier = Modifier
                    .wrapContentHeight()
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium
                    ),
                // check if selected
                selected = currentPageIndex == pagerState.currentPage,
                onClick = {
                    //change the value of the current page index to the new index
                    onUpdateCardContent("wordPage")
                    // makes sure the page changes when the tab is clicked
                    scope.launch { pagerState.animateScrollToPage(page = index) }
                },
                text = {
                    Text(
                        text = word, style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            )
        }
    }
}

@Composable
fun SentencesButton(modifier: Modifier = Modifier, onDisplaySentences : (Boolean) -> Unit) {
    Button(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 13.dp)
            .fillMaxWidth(),
        onClick = { onDisplaySentences(true) },
    ) {
        Text(text = "Sentences")
    }
}

@Composable
fun WordPage(word: Word, onDisplay : (Boolean) -> Unit,
             onChangeToDefinitions : (String) -> Unit) {
    // This is the page that displays the word only
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onChangeToDefinitions("definitions") },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = word.text,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DefinitionsPage(word: Word, onDisplay : (Boolean) -> Unit, onCardFlip: (String) -> Unit) {
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                onCardFlip("wordPage")
            }
        ) {
            Text(
                text = word.text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )

            CustomDivider()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                // Display the definitions
                word.definitions.forEach { definition ->
                    Text(
                        text = "${definition.text}.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            }
        }

        // Aligned at the bottom of the card
        SentencesButton(Modifier.align(Alignment.BottomCenter),
            onDisplaySentences = { onDisplay(it) })

    }
}

@Composable
fun SentencesPage(word: Word, updateCardContent: (String) -> Unit) {
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            // Back arrow icon button
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = {
                        updateCardContent("definitions")
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Arrow",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            Text(
                text = "Sentences",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(10.dp)
                    .fillMaxWidth()
            )
        }
        CustomDivider()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(word.sentences)
            { sentence ->
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        ),
                    ) {

                    Text(
                        text = sentence.text,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 5.dp)
                            .fillMaxWidth()
                    )
                    HorizontalCarousel(resources = sentence.resources)
                }
            }
        }
    }
}