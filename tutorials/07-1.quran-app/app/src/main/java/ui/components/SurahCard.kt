package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.nav.R
import model.Surah
import ui.common.displayMessage
import ui.common.lightGreen
import ui.common.lightYellow
import ui.theme.AppTheme

@Composable
fun SurahCard(surah: Surah, onSelectSurah: (Int) -> Unit) {

    fun Surah.containerColor() =
       if (type == "medinan") lightGreen else lightYellow

    @Composable
    fun Surah.Icon() {
        val imgResourceId = if (surah.type == "medinan")
                         R.drawable.ic_madina
                else
                         R.drawable.ic_mecca
        Image(
            painter = painterResource(id = imgResourceId),
            contentDescription = "Surah Type",
            Modifier.height(50.dp)
        )
    }

    Card(elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = surah.containerColor()
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable {
                onSelectSurah(surah.id)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            surah.Icon()
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "${surah.id}. ${surah.name} - ${surah.englishName}")
                Text(text = "Aya count: ${surah.ayaCount}")
            }
        }
    }
}

@Preview
@Composable
fun SurahCardPreview() {
    val context = LocalContext.current
    AppTheme {
        SurahCard(
            Surah(
                id = 1, name = "الفاتحة",
                englishName = "Al-Fatiha", ayaCount = 7,
                verses = emptyList()
            ),
            onSelectSurah = {
                displayMessage(context, "Selected surah Id: $it")
            }
        )
    }
}