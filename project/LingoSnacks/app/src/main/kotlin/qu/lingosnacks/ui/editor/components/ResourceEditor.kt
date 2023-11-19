package qu.lingosnacks.ui.editor.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import qu.lingosnacks.entity.Resource
import qu.lingosnacks.repository.PackagesRepository
import qu.lingosnacks.ui.editor.types
import qu.lingosnacks.ui.theme.CyanLS
import qu.lingosnacks.ui.theme.LightGreyLS
import qu.lingosnacks.ui.theme.RedLS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResourceEditor(
    resources: List<Resource>,
    onCancelClicked: () -> Unit,
    onConfirmClicked: (Resource) -> Unit,
    resource: Resource = Resource("", "", types[0])
) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf(resource.title) }
    var url by rememberSaveable { mutableStateOf(resource.url) }
    var type by rememberSaveable { mutableStateOf(resource.type) }

    fun isUnique() = PackagesRepository.isUniqueResource(
        title,
        url,
        resources.filterNot { it.title == resource.title })

    fun requiredFilled() = title.isNotBlank() && url.isNotBlank() && isUnique()

    Dialog(
        { onCancelClicked() },
        DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        val fM = LocalFocusManager.current
        val kB = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.7F))
                .imePadding(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, CyanLS),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    if (resource.title == "") "Add Resource" else "Edit Resource",
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    CyanLS,
                    textAlign = TextAlign.Center
                )
                Divider(Modifier.fillMaxWidth(), 1.dp, LightGreyLS)
                OutlinedTextField(value = title, onValueChange = {
                    title = it
                }, label = {
                    Text(text = "Resource Title")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 5.dp, end = 10.dp), singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        fM.moveFocus(FocusDirection.Next)
                        kB?.hide()
                    })
                )
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                    },
                    label = {
                        Text(text = "Resource URL")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        fM.clearFocus()
                        kB?.hide()
                    })
                )

                if (!isUnique()) Text(
                    "Title and URL must be unique",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center,
                    color = RedLS
                )

                TypeDropDown({ type = it }, type)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 10.dp)
                ) {
                    Button(
                        onClick = { onCancelClicked() },
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
                                if (resource.title == "") "New resource created" else "Resource has been edited",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            onConfirmClicked(Resource(url.trim(), title.trim(), type))
                        },
                        enabled = requiredFilled(),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
                    ) {
                        Text(if (resource.title == "") "Add Resource" else "Save Changes")
                    }
                }
            }
        }
    }
}