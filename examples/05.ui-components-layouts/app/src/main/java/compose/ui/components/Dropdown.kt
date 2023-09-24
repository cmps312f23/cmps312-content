package compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(label: String, options: List<String>,
             selectedOption: String,
             onSelectionChange: (String)-> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val trailingIcon = if (expanded)
        // It requires adding this dependency: implementation "androidx.compose.material:material-icons-extended:$compose_version"
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.ArrowDropDown

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = onSelectionChange,
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
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onSelectionChange(option)
                    expanded = false
                }, text = {
                    Text(text = option)
                })
            }
        }
    }
}