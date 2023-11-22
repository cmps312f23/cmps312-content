package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.Definition
import qu.lingosnacks.view.components.Dropdown
import qu.lingosnacks.view.theme.CyanLS

// Hard coded for simplicity. In real app they should come from a DB
val definitionSources = listOf (
    "Oxford English Dictionary",
    "Merriam-Webster Dictionary",
    "Cambridge Dictionary",
    "Wikipedia",
    "Lisan al-Arab",
    "Al-Mawrid"
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DefinitionsTab(definitions: List<Definition>, onAddClicked: (Definition) -> Unit) {
    var definition by rememberSaveable { mutableStateOf("") }
    var source by rememberSaveable { mutableStateOf("") }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    fun isFormReady() = definition.isNotBlank() && source.isNotBlank()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CyanLS),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 4.dp,
            bottomEnd = 4.dp
        )
    ) {
        OutlinedTextField(value = definition, onValueChange = {
            definition = it
        }, label = {
            Text(text = "Definition")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp), singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
            })
        )

        Dropdown(label = "Definition Source", options = definitionSources,
            selectedOption = source,
            onSelectionChange = { source = it })

        Button(
            onClick = {
                onAddClicked(Definition(definition.trim(), source.trim()))
                definition = ""
                source = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            enabled = isFormReady(),
            colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
        ) {
            Text("Add Definition")
        }
    }
}
