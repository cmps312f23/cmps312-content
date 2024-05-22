package ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Surah

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SurahList(surahs: List<Surah>,
              surahType: String,
              searchText: String,
              sortBy: SortBy,
              innerPadding: PaddingValues
) {

    var filteredSurahs = search(surahs, surahType, searchText)
    filteredSurahs = sort(filteredSurahs, sortBy)

    if (filteredSurahs.isEmpty()) {
        Text(text = "No surah found.")
    } else {
        val surahCount = filteredSurahs.size
        val ayaCount = filteredSurahs.sumOf { it.ayaCount }
        /*LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {*/
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            // consume insets as scaffold doesn't do it by default
            //modifier = Modifier.consumeWindowInsets(innerPadding),
            contentPadding = innerPadding
        ) {
            item { ListHeader() }

            items(filteredSurahs) {
                SurahCard(it)
            }

            item { ListFooter(surahCount, ayaCount) }
        }
    }
}

fun search(surahs: List<Surah>,
           surahType: String,
           searchText: String) =
    if (searchText.isNullOrEmpty() && surahType == "All") {
        surahs
    } else {
        surahs.filter {
            (it.englishName.contains(searchText, true) ||
                    it.name.contains(searchText, true) ||
                    searchText.isNullOrEmpty())
                    && (it.type == surahType || surahType == "All")
        }
    }

fun sort(surahs: List<Surah>, sortBy: SortBy) =
    when (sortBy) {
        SortBy.SURAH_NUMBER -> surahs.sortedBy { it.id }
        SortBy.SURAH_NUMBER_DESC -> surahs.sortedByDescending { it.id }
        SortBy.SURAH_NAME -> surahs.sortedBy { it.englishName }
        SortBy.SURAH_NAME_DESC -> surahs.sortedByDescending { it.englishName }
        SortBy.AYA_COUNT -> surahs.sortedBy { it.ayaCount }
        SortBy.AYA_COUNT_DESC -> surahs.sortedByDescending { it.ayaCount }
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

/*
The method below is NOT used. It is added just to be aware of it.
Ok to use a Column to display a small list
For displaying a large list, using a Column/Row layout
can cause performance issues since all the items will be composed
and laid out whether or not they are visible

Use a Lazy List (i.e., LazyColumn o LazyRow) to only compose and lay out
items which are visible on screen
 */
/*
@Composable
fun SurahColumn(surahs: List<Surah>) {
    /*Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier =  Modifier.horizontalScroll(rememberScrollState())*/
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium),
    ) {
        if (surahs.isEmpty()) {
            Text("Loading surahs failed.")
        } else {
            surahs.forEach {
                SurahCard(surah = it)
            }
        }
    }
}
*/