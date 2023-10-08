package ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// More info @ https://www.composables.com/components/material3/exposeddropdownmenubox
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    label: String,
    options: Map<Int, String>,
    selectedOption: Pair<Int, String>,
    onSelectionChange: (Pair<Int, String>)-> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionState by remember { mutableStateOf(selectedOption) }

    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to act as a dropdown
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionState.second,
            onValueChange = {},
            label = { Text( text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.value) },
                    onClick = {
                        onSelectionChange(option.key to option.value)
                        selectedOptionState = option.key to option.value
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

/*@Composable
@Preview
fun DropdownPreview() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    Dropdown(options = options, selectedOption = "Option 1", onSelectionChange = { println(it) })
}
*/