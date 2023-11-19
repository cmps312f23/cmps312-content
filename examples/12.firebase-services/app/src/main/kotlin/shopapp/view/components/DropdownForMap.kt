package shopapp.view.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownForMap(
    label: String = "Options",
    options: Map<String, String>?,
    selectedOptionId: String, onSelectionChange: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    // Execute only when selectedOptionId changes
    LaunchedEffect(selectedOptionId) {
        /*println("Dropdown -> label: $label")
        println("Dropdown -> selectedOptionId: $selectedOptionId")
        println("Dropdown -> options: ${options?.values}")*/
        val selectedOption = options?.get(selectedOptionId) ?: ""
        //println("Dropdown -> selectedOption: $selectedOption")
        selectedOptionText = selectedOption
    }

    // Execute only when options change
    LaunchedEffect(options) {
        val selectedOption = options?.get(selectedOptionId) ?: ""
        selectedOptionText = selectedOption
    }

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
                        onSelectionChange(option.key, option.value)
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
fun DropdownForMapPreview() {
    val options = mapOf( "1" to "Option 1",
        "2" to "Option 2", "3" to "Option 3",
        "4" to "Option 4", "5" to "Option 5")
    DropdownForMap(
        options = options,
        selectedOptionId = "2",
        onSelectionChange = { s1, s2 -> println("$s1, $s2") })
}
  /*  label: String,
    options: Map<String, String>?,
    selectedOptionId: String,
    onSelectionChange: (String, String)-> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val trailingIcon = if (expanded)
        // It requires adding this dependency: implementation "androidx.compose.material:material-icons-extended:$compose_version"
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    val selectedOption = options?.get(selectedOptionId) ?: ""
    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {  },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(text = label) },
            trailingIcon = {
                Icon(trailingIcon, "ArrowIcon",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )
        // Compute the textField width
        val width = with(LocalDensity.current) {
            textFieldSize.width.toDp()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(width)
        ) {
            options?.forEach { option ->
                DropdownMenuItem(onClick = {
                    onSelectionChange(option.key, option.value)
                    expanded = false
                }) {
                    Text(text = option.value)
                }
            }
        }
    }
}*/