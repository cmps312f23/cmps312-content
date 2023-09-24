package compose.ui.tipcalc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonGroup(
    options: List<String>,
    title: String = "",
    selectedOptionIndex: Int,
    onOptionSelected: (Int, String) -> Unit,
    cardBackgroundColor: Color = Color.LightGray
) {
    if (options.isNotEmpty()) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = cardBackgroundColor,
            ),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                if (title.isNotEmpty())
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp),
                    )

                options.forEachIndexed { optionIndex, optionText ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (optionIndex == selectedOptionIndex),
                                onClick = { onOptionSelected(optionIndex, optionText) }
                            )
                            //.padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (optionIndex == selectedOptionIndex),
                            onClick = { onOptionSelected(optionIndex, optionText) }
                        )

                        Text(
                            text = optionText,
                            //modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}