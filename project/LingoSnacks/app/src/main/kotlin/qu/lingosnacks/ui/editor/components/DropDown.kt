package qu.lingosnacks.ui.editor.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.ResourceTypeEnum
import qu.lingosnacks.ui.editor.categories
import qu.lingosnacks.ui.editor.languages
import qu.lingosnacks.ui.editor.levels
import qu.lingosnacks.ui.editor.types
import qu.lingosnacks.ui.theme.DarkGreyLS

@Composable
fun LevelDropDown(onLevelSelected: (String) -> Unit, level: String = levels[0]) {
    var selectedLevel by rememberSaveable { mutableStateOf(level) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row {
        OutlinedTextField(value = selectedLevel,
            onValueChange = { selectedLevel = it },
            enabled = false,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { isExpanded = !isExpanded })
            },
            singleLine = true,
            label = { Text("Level") },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.DarkGray,
                disabledIndicatorColor = DarkGreyLS,
                disabledContainerColor = Color.White,
                disabledTrailingIconColor = Color.DarkGray,
                disabledLabelColor = Color.DarkGray
            ),
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable { isExpanded = !isExpanded }
                .fillMaxWidth(0.5F))

        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            levels.forEach {
                DropdownMenuItem({ Text(it) }, {
                    selectedLevel = it
                    onLevelSelected(it)
                    isExpanded = false
                })
            }
        }
    }
}

@Composable
fun LangDropDown(onLangSelected: (String) -> Unit, language: String = languages[0]) {
    var selectedLang by rememberSaveable { mutableStateOf(language) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row {
        OutlinedTextField(value = selectedLang,
            onValueChange = { selectedLang = it },
            enabled = false,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { isExpanded = !isExpanded })
            },
            singleLine = true,
            label = { Text("Language") },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.DarkGray,
                disabledIndicatorColor = DarkGreyLS,
                disabledContainerColor = Color.White,
                disabledTrailingIconColor = Color.DarkGray,
                disabledLabelColor = Color.DarkGray
            ),
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .fillMaxWidth())

        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            languages.forEach {
                DropdownMenuItem({ Text(it) }, {
                    selectedLang = it
                    onLangSelected(it)
                    isExpanded = false
                })
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CatDropDown(onCatSelected: (String) -> Unit, category: String = categories[0]) {
    var selectedCat by rememberSaveable { mutableStateOf(category) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 10.dp)
    ) {
        OutlinedTextField(
            value = selectedCat,
            onValueChange = {
                selectedCat = it
                val index = categories.indexOf(it.lowercase().capitalize())
                onCatSelected(
                    if (index != -1) categories[index] else it.lowercase().capitalize()
                )
            },
            enabled = true,
            label = { Text("Category") },
            placeholder = { Text("Category") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { isExpanded = !isExpanded })
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                fM.clearFocus()
                kB?.hide()
            })
        )

        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            categories.forEach {
                DropdownMenuItem({ Text(it) }, {
                    selectedCat = it
                    onCatSelected(it)
                    isExpanded = false
                })
            }
        }
    }
}

@Composable
fun TypeDropDown(onTypeSelected: (ResourceTypeEnum) -> Unit, type: ResourceTypeEnum = types[0]) {
    var selectedType by rememberSaveable { mutableStateOf(type) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row {
        OutlinedTextField(value = selectedType.name,
            onValueChange = { selectedType = ResourceTypeEnum.valueOf(it) },
            enabled = false,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { isExpanded = !isExpanded })
            },
            singleLine = true,
            label = { Text("Resource Type") },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.DarkGray,
                disabledIndicatorColor = DarkGreyLS,
                disabledContainerColor = Color.White,
                disabledTrailingIconColor = Color.DarkGray,
                disabledLabelColor = Color.DarkGray
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .clickable { isExpanded = !isExpanded }
                .fillMaxWidth()
        )

        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            types.forEach {
                DropdownMenuItem({ Text(it.name) }, {
                    selectedType = it
                    onTypeSelected(it)
                    isExpanded = false
                })
            }
        }
    }
}
