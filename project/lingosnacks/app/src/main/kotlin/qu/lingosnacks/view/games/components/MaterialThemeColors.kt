package qu.lingosnacks.view.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qu.lingosnacks.view.theme.AppTheme

@Composable
fun MaterialColors(){
    val colorsList = mapOf<Int, Color>(
        1 to MaterialTheme.colorScheme.primary,
        2 to MaterialTheme.colorScheme.secondary,
        3 to MaterialTheme.colorScheme.tertiary,
        4 to MaterialTheme.colorScheme.onPrimary,
        5 to MaterialTheme.colorScheme.onSecondary,
        6 to MaterialTheme.colorScheme.onTertiary,
        7 to MaterialTheme.colorScheme.onBackground,
        8 to MaterialTheme.colorScheme.onSurface,
        9 to MaterialTheme.colorScheme.background,
        10 to MaterialTheme.colorScheme.surface,
        11 to MaterialTheme.colorScheme.primaryContainer,
        12 to MaterialTheme.colorScheme.secondaryContainer,
        13 to MaterialTheme.colorScheme.tertiaryContainer,
        14 to MaterialTheme.colorScheme.onPrimaryContainer,
        15 to MaterialTheme.colorScheme.onSecondaryContainer,
        16 to MaterialTheme.colorScheme.onTertiaryContainer,
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)) {
        LazyVerticalGrid(columns = GridCells.Fixed(3) ) {
            items(colorsList.keys.toList()) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .background(colorsList[it]!!, MaterialTheme.shapes.large)
                    .aspectRatio(1f)) {
                    Text(fontWeight = FontWeight.Bold,text = "${it}", modifier = Modifier.align(Alignment.Center), color = Color.LightGray, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Preview
@Composable
fun WithAppTheme(){
    AppTheme {
        MaterialColors()
    }
}

@Preview
@Composable
fun WithoutAppTheme(){
    MaterialColors()
}

@Preview
@Composable
fun testColors () {

    Box(modifier = Modifier.aspectRatio(1f).padding(15.dp).background(MaterialTheme.colorScheme.primaryContainer)){
        Box(modifier = Modifier.aspectRatio(1f).padding(15.dp).background(MaterialTheme.colorScheme.background)){
            Box(modifier = Modifier.aspectRatio(1f).padding(15.dp).background(MaterialTheme.colorScheme.primaryContainer)) {
            }
        }
    }


}

