package ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.CarRepo
import ui.components.CarCard
import ui.main.ui.components.Utils
import ui.theme.AppTheme

@Composable
fun CarsList() {
    val context = LocalContext.current

    val onListItemClick = { text : String ->
        Utils.toast(context, text)
    }

    LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(CarRepo.carBrands) {
            CarCard(carBrand = it, onItemClick = onListItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarsListPreview() {
    AppTheme {
        CarsList()
    }
}