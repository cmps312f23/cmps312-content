package qu.lingosnacks.ui.editor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import qu.lingosnacks.ui.theme.DarkGreyLS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(onSearch: (String) -> Unit) {
    var search by rememberSaveable { mutableStateOf("") }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    TextField(
        value = search,
        onValueChange = {
            search = it
            onSearch(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp, 10.dp, 5.dp)
            .shadow(elevation = 5.dp, clip = true),
        textStyle = TextStyle(color = DarkGreyLS, fontSize = 18.sp),
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
            if (search.isNotBlank())
                IconButton(
                    // Remove text from TextField when you press the 'X' icon
                    onClick = {
                        search = ""
                        onSearch("")
                        fM.clearFocus()
                        kB?.hide()
                    }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
        },
        singleLine = true,
        shape = RectangleShape,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                fM.clearFocus()
                kB?.hide()
            }
        )
    )
}