package qu.lingosnacks.view.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.view.components.Dropdown
import qu.lingosnacks.view.editor.components.WordList
import qu.lingosnacks.view.theme.CyanLS
import qu.lingosnacks.viewmodel.AuthViewModel
import qu.lingosnacks.viewmodel.PackageViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PackageEditorScreen(
    packageId: String = "0",
    packageViewModel: PackageViewModel,
    authViewModel: AuthViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val editorMode =  if (packageId == "0") "Add" else "Edit"

    val levels = packageViewModel.getLevels()
    val languages = packageViewModel.getLanguages()
    val categories = packageViewModel.getCategories()

    val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle().value

    var learningPackage = LearningPackage()
    if (editorMode == "Edit") {
        val lPackage = packageViewModel.getPackage(packageId)
        if (lPackage != null)
            learningPackage = lPackage
        else
            displayMessage(context, "Package $packageId not found")
    }

    var title by remember { mutableStateOf(learningPackage.title) }
    var description by remember { mutableStateOf(learningPackage.description) }
    var level by remember { mutableStateOf(learningPackage.level) }
    var language by remember { mutableStateOf(learningPackage.language) }
    var category by remember { mutableStateOf(learningPackage.category) }
    var iconUrl by remember { mutableStateOf(learningPackage.iconUrl ?: "") }
    var words by remember { mutableStateOf(learningPackage.words.toList()) }
    var openWordEditor by remember { mutableStateOf(false) }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    if (openWordEditor)
        WordEditor(words,
            onCancel = { openWordEditor = false },
            onConfirm = {
                openWordEditor = false
                words = words.plusElement(it)
            }
        )

    fun reset() {
        title = learningPackage.title
        description = learningPackage.description
        level = learningPackage.level
        language = learningPackage.language
        category = learningPackage.category
        iconUrl = learningPackage.iconUrl
        words = learningPackage.words.toList()
    }

    fun isFormReady() =
        title.isNotBlank() && description.isNotBlank() && category.isNotBlank() && words.isNotEmpty()

    fun upsertPackage() {
        learningPackage = learningPackage.copy(
            category = category.trim(),
            description = description.trim(),
            iconUrl = iconUrl.trim(),
            language = language,
            level = level,
            title = title.trim(),
            words = words.toMutableList(),
            author = currentUser?.email ?: "",
            lastUpdatedDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        )
        packageViewModel.upsertPackage(learningPackage)
        val message = if (editorMode == "Add")
            "New package created"
        else
            "Package changes saved"
        displayMessage(context, message)

        onNavigateBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(ScrollState(0), true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(  text = if (editorMode == "Add") "Add Package" else "Edit Package")
        OutlinedTextField(value = title, onValueChange = {
            title = it
        }, label = {
            Text(text = "Title")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp), singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
            })
        )
        OutlinedTextField(value = description, onValueChange = {
            description = it
        }, label = {
            Text(text = "Description")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), maxLines = 3,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
            })
        )

        OutlinedTextField(value = iconUrl, onValueChange = {
            iconUrl = it
        }, label = {
            Text(text = "Icon URL")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                fM.clearFocus()
                kB?.hide()
            })
        )

        Dropdown(label = "Level", options = levels, selectedOption = level,
                onSelectionChange = { level = it })

        Dropdown(label = "Language", options = languages, selectedOption = language,
            onSelectionChange = { language = it })

        Dropdown(label = "Category", options = categories, selectedOption = category,
            onSelectionChange = { category = it })

        WordList(words, { openWordEditor = true },
            { old, new ->
                val temp = words.toMutableList()
                temp[temp.indexOf(old)] = new
                words = temp.toList()
            },
            { words = words.minusElement(it) })
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Button(
                onClick = {
                    val message = if (editorMode == "Add")
                            "New package not created"
                        else
                            "Package changes discarded"
                    displayMessage(context, message)
                    onNavigateBack()
                },
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, contentColor = CyanLS
                ),
                border = BorderStroke(1.dp, CyanLS),
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text("Cancel", modifier = Modifier.padding(horizontal = 2.5.dp))
            }

            Button(
                onClick = {
                    upsertPackage()
                },
                enabled = isFormReady(),
                colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
            ) {
                Text("Submit")
            }
        }
    }
}