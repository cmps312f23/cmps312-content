package qu.lingosnacks.ui.editor

import android.widget.Toast
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.repository.PackagesRepository
import qu.lingosnacks.repository.UserRepository
import qu.lingosnacks.ui.editor.components.CatDropDown
import qu.lingosnacks.ui.editor.components.LangDropDown
import qu.lingosnacks.ui.editor.components.LevelDropDown
import qu.lingosnacks.ui.editor.components.WordList
import qu.lingosnacks.ui.navigation.NavDestination
import qu.lingosnacks.ui.theme.CyanLS
import qu.lingosnacks.ui.theme.AppTheme
import qu.lingosnacks.ui.theme.RedLS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PackageEditorScreen(
    onConfirmClicked: (String, LearningPackage) -> Unit,
    navigateTo: (String) -> Unit,
    packageId: Int = -1
) {
    val context = LocalContext.current
    var learningPackage =
        if (packageId == -1) newPackage else PackagesRepository.getPackageById(packageId)!!
    var title by rememberSaveable { mutableStateOf(learningPackage.title) }
    var description by rememberSaveable { mutableStateOf(learningPackage.description) }
    var level by rememberSaveable { mutableStateOf(learningPackage.level) }
    var language by rememberSaveable { mutableStateOf(learningPackage.language) }
    var category by rememberSaveable { mutableStateOf(learningPackage.category) }
    var iconUrl by rememberSaveable { mutableStateOf(learningPackage.iconUrl ?: "") }
    var words by rememberSaveable { mutableStateOf(learningPackage.words.toList()) }
    var openCreate by rememberSaveable { mutableStateOf(false) }
    val fM = LocalFocusManager.current
    val kB = LocalSoftwareKeyboardController.current

    if (openCreate) WordEditor(words, { openCreate = false }, {
        openCreate = false
        words = words.plusElement(it)
    })

    fun reset() {
        title = learningPackage.title
        description = learningPackage.description
        level = learningPackage.level
        language = learningPackage.language
        category = learningPackage.category
        iconUrl = learningPackage.iconUrl ?: ""
        words = learningPackage.words.toList()
    }

    fun isUnique() =
        PackagesRepository.isUniquePackage(title, description, learningPackage.packageId)

    fun requiredFilled() =
        title.isNotBlank() && description.isNotBlank() && category.isNotBlank() && words.isNotEmpty() && isUnique()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(ScrollState(0), true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(value = title, onValueChange = {
            title = it
        }, label = {
            Text(text = "Package Title")
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
            Text(text = "Package Description")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), maxLines = 3,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                fM.moveFocus(FocusDirection.Next)
                kB?.hide()
            })
        )

        if (!isUnique()) Text(
            "Title and description must be unique",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            textAlign = TextAlign.Center,
            color = RedLS
        )

        OutlinedTextField(value = iconUrl, onValueChange = {
            iconUrl = it
        }, label = {
            Text(text = "Package Icon URL")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                fM.clearFocus()
                kB?.hide()
            })
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            LevelDropDown({ level = it }, level)
            LangDropDown({ language = it }, language)
        }
        CatDropDown({ category = it }, category)
        WordList(words, { openCreate = true },
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
                    Toast.makeText(
                        context,
                        if (learningPackage.packageId == -1) "New package not created" else "Package changes discarded",
                        Toast.LENGTH_SHORT
                    ).show()
                    reset()
                    navigateTo(NavDestination.PackagesScreen.route)
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
                    Toast.makeText(
                        context,
                        if (learningPackage.packageId == -1) "New package created" else "Package has been edited",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (learningPackage.packageId == -1)
                        onConfirmClicked(
                            "add",
                            newPackage.copy(
                                author = UserRepository.currentUser?.email ?: "t2@test.com",
                                category = category.trim(),
                                description = description.trim(),
                                iconUrl = if (iconUrl.trim().isBlank()) null else iconUrl.trim(),
                                language = language,
                                level = level,
                                title = title.trim(),
                                words = words.toMutableList()
                            )
                        )
                    else
                        onConfirmClicked(
                            "edit",
                            learningPackage.copy(
                                category = category.trim(),
                                description = description.trim(),
                                iconUrl = if (iconUrl.trim().isBlank()) null else iconUrl.trim(),
                                language = language,
                                level = level,
                                title = title.trim(),
                                words = words.toMutableList()
                            )
                        )
                    reset()
                    navigateTo(NavDestination.MyPackagesScreen.route)
                },
                enabled = requiredFilled(),
                colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
            ) {
                Text(if (learningPackage.packageId == -1) "Publish" else "Save Changes")
            }
        }
    }
}


@Preview
@Composable
fun PackageEditorScreenPreview() {
    AppTheme {
        Surface {
            PackageEditorScreen({ a, b -> {} }, {})
        }
    }
}

val levels = listOf("Beginner", "Easy", "Intermediate", "Average", "Advanced", "Hard")
val languages = listOf("English", "Arabic", "French", "Spanish", "Italian")
val categories = listOf("Colors", "Countries", "Food", "Health", "Places", "Seasons")

//To test edit functionality
var existingPackage = PackagesRepository.getPackageById(3)!!

//Default new package for add functionality
var newPackage = LearningPackage(
    -1,
    UserRepository.currentUser?.email ?: "test@test.com",
    categories[0],
    "",
    "",
    language = languages[0],
    level = levels[0],
    title = ""
)