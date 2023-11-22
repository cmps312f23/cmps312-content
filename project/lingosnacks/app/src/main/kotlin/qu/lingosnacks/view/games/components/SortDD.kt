package qu.lingosnacks.view.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortDD(selectedOption: String, onSelectedOptionChange: (String) -> Unit) {

    var sortByOptions = listOf("All","Beginner","Easy","Average", "Downloaded")

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(

                imageVector = Icons.Default.Menu,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Sort By",
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(10.dp)
            )
            Text(text = selectedOption, color = MaterialTheme.colorScheme.onPrimary)

        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sortByOptions.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSelectedOptionChange(it)
                    },
                    text = { Text(text = it) }
                )
            }
        }
    }
}