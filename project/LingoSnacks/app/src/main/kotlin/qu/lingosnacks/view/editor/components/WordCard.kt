package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.Word
import qu.lingosnacks.view.editor.WordEditor
import qu.lingosnacks.view.theme.LightGreyLS
import qu.lingosnacks.view.theme.PinkLS

@Composable
fun WordCard(
    word: Word,
    words: List<Word>,
    onEditClicked: (Word, Word) -> Unit,
    onDeleteClicked: (Word) -> Unit
) {
    var openEditor by rememberSaveable { mutableStateOf(false) }

    if (openEditor) WordEditor(words, { openEditor = false }, {
        openEditor = false
        onEditClicked(word, it)
    }, word)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, PinkLS),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(start = 7.5.dp, end = 5.dp)
        ) {
            Text(word.text, Modifier.padding(top = 5.dp, end = 10.dp, bottom = 5.dp), PinkLS)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Edit, null, Modifier.clickable { openEditor = true }, PinkLS)
                Divider(
                    Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 2.5.dp)
                        .width(1.dp),
                    color = PinkLS
                )
                Icon(
                    Icons.Default.Close,
                    null,
                    Modifier.clickable { onDeleteClicked(word) },
                    LightGreyLS
                )
            }
        }
    }
}
