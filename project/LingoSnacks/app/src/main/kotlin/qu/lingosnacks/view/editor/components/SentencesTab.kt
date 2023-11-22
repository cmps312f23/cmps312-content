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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.Resource
import qu.lingosnacks.entity.Sentence
import qu.lingosnacks.view.theme.CyanLS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SentencesTab(sentences: List<Sentence>, onAddClicked: (Sentence) -> Unit) {
    var sentence by rememberSaveable { mutableStateOf("") }
    var resources by rememberSaveable { mutableStateOf(listOf<Resource>()) }
    var openCreate by rememberSaveable { mutableStateOf(false) }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    if (openCreate) ResourceEditor(resources, { openCreate = false }, {
        openCreate = false
        resources = resources.plusElement(it)
    })

    fun isFormReady() = sentence.isNotBlank() && resources.isNotEmpty()

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
        OutlinedTextField(value = sentence, onValueChange = {
            sentence = it
        }, label = {
            Text(text = "Sentence")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp), singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                fM.clearFocus()
                kB?.hide()
            })
        )

        ResourceList(
            resources,
            { openCreate = true },
            { old, new ->
                val temp = resources.toMutableList()
                temp[temp.indexOf(old)] = new
                resources = temp.toList()
            },
            { resources = resources.minusElement(it) })
        Button(
            onClick = {
                onAddClicked(Sentence(sentence.trim(), resources.toMutableList()))
                sentence = ""
                resources = listOf()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            enabled = isFormReady(),
            colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
        ) {
            Text("Add Sentence")
        }
    }
}