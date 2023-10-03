package ui.staggeredgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme
import kotlin.random.Random

data class BoxProperties(
    val color: Color,
    val width: Dp
)

@Composable
fun MainScreen() {
    val boxes = (1 .. 50).map {
        BoxProperties(
            width = Random.nextInt(50, 200).dp,
            color = Color(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255),
                255
            )
        )
    }

    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalItemSpacing =  8.dp,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(boxes) { boxProps ->
            GridItem(boxProps = boxProps)
        }
    }
}

@Composable
fun GridItem(boxProps: BoxProperties) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .width(boxProps.width)
        .clip(RoundedCornerShape(10.dp))
        .background(boxProps.color)
    )
}

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun StaggeredGridPreview() {
    AppTheme {
        MainScreen()
    }
}