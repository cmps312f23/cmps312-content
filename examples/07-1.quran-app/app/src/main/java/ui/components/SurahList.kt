package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.viewmodel.SurahViewModel

@Composable
fun SurahList(viewModel: SurahViewModel, onSelectSurah: (Int) -> Unit) {
    /* Get an instance of the shared viewModel
       Make the activity the store owner of the viewModel
       to ensure that the same viewModel instance is used for all destinations
    */
    //val surahViewModel = SurahViewModel(LocalContext.current)
    //val viewModel = viewModel<SurahViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //item { ListHeader() }

        items(viewModel.surahs) {
            SurahCard(surah = it, onSelectSurah = { surahId ->
                onSelectSurah(surahId)
            })
        }

        item { ListFooter(viewModel.surahCount, viewModel.ayaCount) }
    }
}

@Composable
fun ListHeader() {
    Text(
        text = "سور القرآن الكريم",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun ListFooter(surahCount: Int, ayaCount: Int) {
    Text(
        text = "$surahCount سورة  - $ayaCount آية ",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge
    )
}