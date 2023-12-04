package qu.lingosnacks.view.games.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@Composable
fun FlashCard(pageCount: Int, pagerState: PagerState,
              onUpdateCurrentWord: (Int) -> Unit,
              onSentencesClicked: (Boolean) -> Unit,
              pageContent : @Composable () -> Unit) {
    val gradient = Brush.radialGradient(
        0.4f to MaterialTheme.colorScheme.onPrimary,
        1.2f to MaterialTheme.colorScheme.tertiaryContainer,
        radius = 1500.0f,
        tileMode = TileMode.Repeated
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // this pager will display the individual pages aka flash cards
        // this card contains the definitions and sentences pages
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            pageSpacing = 0.dp,
            //horizontalAlignment = horizontalAlignment,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(6.dp),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            //flingBehavior = PagerDefaults.flingBehavior(state = state),
            key = null,
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                Orientation.Horizontal
            ),
            pageContent = //@Composable fun PagerScope.(tabIndex: Int) {
            {
                // this card contains the definitions and sentences pages
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(5.dp)
                        .shadow(
                            3.dp,
                            shape = MaterialTheme.shapes.large,
                            ambientColor = Color.DarkGray
                        )
                        .background(gradient, MaterialTheme.shapes.large),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    onUpdateCurrentWord(pagerState.currentPage)
                    pageContent()
                }
            }
        )
    }
}