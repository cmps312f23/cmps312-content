package ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(searchText: String, onValueChange: (String) -> Unit,
              modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        value = searchText,
        onValueChange = onValueChange,
        //textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onValueChange("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // Rounded corners
        /*colors = TextFieldDefaults.textFieldColors(
            //textColor = Color.White,
            cursorColor = Color.White,
            //leadingIconColor = Color.White,
            //trailingIconColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary
        )*/
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    var searchText by remember { mutableStateOf("") }
    SearchBox(searchText, onValueChange = { searchText = it})
}