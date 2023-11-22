package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.Definition
import qu.lingosnacks.view.theme.DarkGreyLS
import qu.lingosnacks.view.theme.LightGreyLS

@Composable
fun DefinitionList(
    definitions: List<Definition>,
    onEditClicked: (Definition, Definition) -> Unit,
    onDeleteClicked: (Definition) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, DarkGreyLS),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            "Definitions",
            Modifier
                .padding(15.dp)
                .fillMaxWidth(), Color.DarkGray
        )
        if (definitions.isEmpty()) Text(
            "No Definitions Added",
            Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxWidth(),
            LightGreyLS,
            textAlign = TextAlign.Center
        )
        else Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            definitions.forEach { DefinitionCard(it, definitions, onEditClicked, onDeleteClicked) }
        }
    }
}