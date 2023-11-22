package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.Word
import qu.lingosnacks.view.theme.CyanLS
import qu.lingosnacks.view.theme.DarkGreyLS
import qu.lingosnacks.view.theme.LightGreyLS

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordList(
    words: List<Word>,
    onAddClicked: () -> Unit,
    onEditClicked: (Word, Word) -> Unit,
    onDeleteClicked: (Word) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, DarkGreyLS),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text("Words", Modifier.padding(start = 5.dp), Color.DarkGray)
            Button(
                onClick = { onAddClicked() },
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
            ) {
                Text("Add Words")
            }
        }
        if (words.isEmpty()) Text(
            "No Words in Package",
            Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxWidth(),
            LightGreyLS,
            textAlign = TextAlign.Center
        )
        else FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            Arrangement.spacedBy(5.dp)
        ) {
            words.forEach { WordCard(it, words, onEditClicked, onDeleteClicked) }
        }
    }
}