package ui.grid

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.CarRepo
import ui.components.CarCard
import ui.main.ui.components.Utils
import ui.theme.AppTheme

@Composable
fun CarsGrid() {
    val context = LocalContext.current

    val onListItemClick = { text : String ->
        Utils.toast(context, text)
    }

    //LazyColumn {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp) //.Fixed(2)
    ) {
        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            Text(text = "Popular car brands")
        }

        items(CarRepo.carBrands) { 
            CarCard(carBrand = it, onItemClick = onListItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarsGridPreview() {
    AppTheme {
        CarsGrid()
    }
}