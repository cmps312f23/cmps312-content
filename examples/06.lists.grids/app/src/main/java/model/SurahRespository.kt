package model

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object SurahRepository {
    private var surahs = listOf<Surah>()

    fun getSurahs(context: Context): List<Surah> {
        if (surahs.isEmpty()) {
            val surahsJson = context.assets
                .open("surahs.json")
                .bufferedReader()
                .use { it.readText() }
            surahs = Json.decodeFromString(surahsJson)
        }
        return surahs
    }
}