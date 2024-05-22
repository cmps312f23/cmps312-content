package ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    searchText: String, onSearchTextChange: (String) -> Unit,
    surahType: String, onSurahTypeChange: (String) -> Unit,
    onSortByChange: (SortBy) -> Unit
) {
    val surahTypes = listOf(
        "All",
        "Meccan",
        "Medinan"
    )

    TopAppBar(
        title = { Text("") },
        actions = {
            SearchBox(searchText, onSearchTextChange, modifier = Modifier.weight(5F))

            Dropdown(
                modifier = Modifier.weight(4F),
                options = surahTypes,
                selectedOption = surahType,
                onSelectionChange = onSurahTypeChange
            )

            TopBarMenu(onSortByChange, modifier = Modifier.weight(1F))
        }
    )
}

@Composable
fun TopBarMenu(
    onSortByChange: (SortBy) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More..."
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            SortBy.values().forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortByChange(option)
                }, text = {
                    Text(option.label)
                })
            }
        }
    }
}