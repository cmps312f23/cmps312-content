package json.surah

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object SurahRepository {
    var surahs = listOf<Surah>()
    init {
        val filePath = "data/surahs.json"
        val jsonData = File(filePath).readText()
        surahs = Json.decodeFromString(jsonData)
    }

    val totalAyat = surahs.sumOf { it.ayaCount }

    // Get Surah Count by Surah type (Meccan vs. Medinan)
    val surahCountByType = surahs.groupingBy { it.type }.eachCount()

    // Get Aya Count by Surah type (Meccan vs. Medinan)
    val ayaCountByType = surahs.groupingBy { it.type }.fold(0) { count, surah -> count + surah.ayaCount }

    // Get Surahs having more than ayaCount
    fun getSurahs(ayaCount: Int) = surahs.filter { it.ayaCount >= ayaCount }.sortedByDescending { it.ayaCount }

    // Get Surahs by surahType
    fun getSurahs(surahType: String) = surahs.filter { it.type.equals(surahType, true) }

    fun getLongestSurah() = surahs.maxByOrNull { it.ayaCount }
}