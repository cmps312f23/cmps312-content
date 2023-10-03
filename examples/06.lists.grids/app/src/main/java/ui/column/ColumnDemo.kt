package ui.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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

   Column( modifier =
        Modifier.verticalScroll(rememberScrollState())) {
        CarRepo.carBrands.forEach() {
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