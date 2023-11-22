package qu.lingosnacks.view.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearch: (String) -> Unit) {
    Row() {
        TextField(
            maxLines = 1,
            value = (searchText),
            onValueChange = { onSearch(it) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.secondary)
            },
            trailingIcon = {
                if (searchText.isNotEmpty())
                    Icon(imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable { onSearch("") }
                    )
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)

        )
//        SortDD("Sort By", onOptionSelected = {})
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(searchText = "", onSearch = {})
}