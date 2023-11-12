package shopapp.view.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// More info @ https://www.composables.com/components/material3/exposeddropdownmenubox
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    label: String = "Options",
    options: Map<Long, String>?,
    selectedOptionId: Long, onSelectionChange: (Long)-> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = options?.get(selectedOptionId) ?: ""
    var selectedOptionText by remember { mutableStateOf(selectedOption) }

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
            value = selectedOptionText,
            onValueChange = {},
            label = { Text( text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options?.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.value) },
                    onClick = {
                        onSelectionChange(option.key)
                        selectedOptionText = option.value
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
@Preview
fun DropdownPreview() {
    val options = mapOf( 1L to "Option 1",
        2L to "Option 2", 3L to "Option 3",
        4L to "Option 4", 5L to "Option 5")
    Dropdown(options = options, selectedOptionId = 2, onSelectionChange = { println(it) })
}