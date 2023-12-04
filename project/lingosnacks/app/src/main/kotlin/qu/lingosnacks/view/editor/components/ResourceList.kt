package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import qu.lingosnacks.entity.Resource
import qu.lingosnacks.view.theme.CyanLS
import qu.lingosnacks.view.theme.DarkGreyLS
import qu.lingosnacks.view.theme.LightGreyLS

@Composable
fun ResourceList(
    resources: List<Resource>,
    onAddClicked: () -> Unit,
    onEditClicked: (Resource, Resource) -> Unit,
    onDeleteClicked: (Resource) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp),
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
            Text("Resources", Modifier.padding(start = 5.dp), Color.DarkGray)
            Button(
                onClick = { onAddClicked() },
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = CyanLS)
            ) {
                Text("Add Resources")
            }
        }
        if (resources.isEmpty()) Text(
            "No Resources Added",
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
            resources.forEach { ResourceCard(it, resources, onEditClicked, onDeleteClicked) }
        }
    }
}