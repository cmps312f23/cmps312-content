package ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ui.theme.AppTheme
import model.SurahRepository

enum class SortBy(val label: String) {
    SURAH_NUMBER("Sort by Surah number"),
    SURAH_NUMBER_DESC("Sort by Surah number - descending"),
    SURAH_NAME("Sort by Surah name"),
    SURAH_NAME_DESC("Sort by Surah name - descending"),
    AYA_COUNT("Sort by aya count"),
    AYA_COUNT_DESC("Sort by by aya count - descending")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SurahScreen() {
    var searchText by remember { mutableStateOf("") }
    var surahType by remember {
        mutableStateOf("All")
    }

    var sortBy by remember {
        mutableStateOf(SortBy.SURAH_NUMBER)
    }

    Scaffold(
        topBar = {
            TopBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                surahType,
                onSurahTypeChange = { surahType = it },
                onSortByChange = { sortBy = it }
            )
        },
        content = { innerPadding ->
                val surahs = SurahRepository.getSurahs(LocalContext.current)
                SurahList(surahs, surahType, searchText, sortBy, innerPadding)
        }
    )
}

@Preview
@Composable
fun SurahListScreenPreview() {
    AppTheme {
        SurahScreen()
    }
}