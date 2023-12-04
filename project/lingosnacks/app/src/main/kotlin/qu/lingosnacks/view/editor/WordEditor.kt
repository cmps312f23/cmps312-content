package qu.lingosnacks.view.editor

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import qu.lingosnacks.entity.Word
import qu.lingosnacks.view.editor.components.DefinitionList
import qu.lingosnacks.view.editor.components.DefinitionsTab
import qu.lingosnacks.view.editor.components.SentenceList
import qu.lingosnacks.view.editor.components.SentencesTab
import qu.lingosnacks.view.theme.AppTheme
import qu.lingosnacks.view.theme.CyanLS
import qu.lingosnacks.view.theme.LightGreyLS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WordEditor(
    words: List<Word>,
    onCancel: () -> Unit,
    onConfirm: (Word) -> Unit,
    word: Word = Word("")
) {
    val context = LocalContext.current

    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var text by rememberSaveable { mutableStateOf(word.text) }
    var sentences by rememberSaveable { mutableStateOf(word.sentences.toList()) }
    var definitions by rememberSaveable { mutableStateOf(word.definitions.toList()) }

    fun reset() {
        text = word.text
        sentences = word.sentences.toList()
        definitions = word.definitions.toList()
    }

    fun isFormReady() =
        text.isNotBlank() && sentences.isNotEmpty() && definitions.isNotEmpty()

    Dialog(
        { onCancel() },
        DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        val fM = LocalFocusManager.current
        val kB = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .padding(top = 75.dp, bottom = 65.dp)
                .background(Color.White)
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(ScrollState(0), true)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(value = text, onValueChange = {
                text = it
            }, label = {
                Text(text = "Word")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp), singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    fM.clearFocus()
                    kB?.hide()
                })
            )

            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), 1.dp, LightGreyLS
            )
            TabRow(selectedTab) {
                Tab(selectedTab == 0, { selectedTab = 0 }, text = { Text("Definitions") })
                Tab(selectedTab == 1, { selectedTab = 1 }, text = { Text("Sentences") })
            }
            if (selectedTab == 1) {
                SentencesTab(sentences) { sentences = sentences.plusElement(it) }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp), 1.dp, LightGreyLS
                )
                SentenceList(sentences,
                    { old, new ->
                        val temp = sentences.toMutableList()
                        temp[temp.indexOf(old)] = new
                        sentences = temp.toList()
                    },
                    { sentences = sentences.minusElement(it) })
            } else {
                DefinitionsTab(definitions) { definitions = definitions.plusElement(it) }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp), 1.dp, LightGreyLS
                )
                DefinitionList(
                    definitions,
                    { old, new ->
                        val temp = definitions.toMutableList()
                        temp[temp.indexOf(old)] = new
                        definitions = temp.toList()
                    },
                    { definitions = definitions.minusElement(it) })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Button(
                    onClick = {
                        reset()
                        onCancel()
                    }, enabled = true, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, contentColor = CyanLS
                    ), border = BorderStroke(1.dp, CyanLS), modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text("Cancel", modifier = Modifier.padding(horizontal = 2.5.dp))
                }
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            if (word.text == "") "Word added to package" else "Word has been edited",
                            Toast.LENGTH_SHORT
                        ).show()
                        onConfirm(
                            Word(
                                text.trim(),
                                definitions.toMutableList(),
                                sentences.toMutableList()
                            )
                        )
                        reset()
                    },
                    enabled = isFormReady(),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
                ) {
                    Text(if (word.text == "") "Add Word" else "Save Changes")
                }
            }
        }
    }
}

@Preview
@Composable
fun WordEditorScreenPreview() {
    AppTheme {
        Surface {
            WordEditor(listOf(), {}, {})
        }
    }
}