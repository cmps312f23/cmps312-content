package maps.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import maps.entity.MenuOption

@Composable
fun DropDownMenu(onMenuItemClick: (MenuOption) -> Unit,
                 modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    //Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
    Box(modifier = modifier) {
        Button(onClick = {
            expanded = true
        }) {
            Text(text = "More...")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            MenuOption.values().forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onMenuItemClick(option)
                    },
                    text = { Text(option.label) }
                )
            }
        }
    }
}