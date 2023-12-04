package qu.lingosnacks.view.games.unscramble


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import qu.lingosnacks.entity.Sentence
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.theme.PalePurple
import qu.lingosnacks.view.theme.Tuna
import qu.lingosnacks.viewmodel.PackageViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UnscrambleGameScreen(
    sentences: List<Sentence>,
    currentUser: User,
    packageId: String,
    packageViewModel: PackageViewModel
    ) {
    //val sentences = packageViewModel.getSentences(learningPackage.packageId)
    //val wordsList = packageViewModel.getPackage(learningPackage.packageId).words

    val pageCount = sentences.size

    val pagerState = rememberPagerState(pageCount = {
        pageCount
    })
    val coroutineScope = rememberCoroutineScope()

    Box {
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) { index ->
                    UnscrambleSentenceForm(
                        sentences[index].text,
                        currentUser,
                        packageId,
                        packageViewModel
                    )
        }
        ElevatedButton(
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                2.dp,
                Tuna
            ),
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage - 1
                    )
                }
            },
            contentPadding = PaddingValues(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = Tuna
            )
        }
        ElevatedButton(
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterEnd),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                2.dp,
                Tuna
            ),
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            contentPadding = PaddingValues(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Tuna
            )
        }
    }

    Row(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Tuna
                else PalePurple
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(20.dp)
            )
        }
    }
}

