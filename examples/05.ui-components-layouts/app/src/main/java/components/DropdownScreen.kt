package compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropdownScreen() {
    val countries = listOf(
        "Australia",
        "Qatar",
        "Japan",
        "United States",
        "India",
    )
    var selectedCountry by remember {
        mutableStateOf(countries[1])
    }

    Column(
        Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Dropdown(label = "Country",
            options = countries,
            selectedOption = selectedCountry,
            onSelectionChange = { selectedCountry = it}
        )
        
        Text(text = "Selected country: $selectedCountry")
    }
}

@Preview
@Composable
fun DropdownScreenPreview() {
    DropdownScreen()
}