package ui.quran

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SurahScreen(onSelectSurah: (Int) -> Unit) {
    /* Get an instance of the shared viewModel
       Make the activity the store owner of the viewModel
       to ensure that the same viewModel instance is used for all destinations
    */
    //val surahViewModel = SurahViewModel(LocalContext.current)
    val surahViewModel = viewModel<SurahViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val surahs = surahViewModel.surahs

    if (surahs.isEmpty()) {
        Text("No surah found.")
    } else {
        val surahCount = surahs.size
        val ayaCount = surahs.sumOf { it.ayaCount }
        /*LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {*/
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ListHeader() }

            items(surahs) {
                SurahCard(surah = it, onSelectSurah = { surahId ->
                    onSelectSurah(surahId)
                } )
            }

            item { ListFooter(surahCount, ayaCount) }
        }
    }
}

@Composable
fun ListHeader() {
    Text(
        text = "سور القرآن الكريم",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Blue,
            textDirection = TextDirection.Rtl
        )
    )
}

@Composable
fun ListFooter(surahCount: Int, ayaCount: Int) {
    Text(
        text = "$surahCount سورة  - $ayaCount آية ",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Blue,
            textDirection = TextDirection.Rtl
        )
    )
}