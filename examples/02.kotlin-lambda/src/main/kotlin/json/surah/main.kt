package json.surah

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun main() {
    val fatiha = Surah(1, "الفاتحة", "Al-Fatiha", 7, "Meccan")

    // Transform an instance of Surah class to a JSON string
    // Converting object to a json string is called Serialize/Encode
    // json = standard data format understood by all languages: JavaScript, Java, C#, Python...
    val surahJson = Json.encodeToString(fatiha)
    println("Surah Json: $surahJson")

    // Converting a json string to an object is called Deserialize/Decode
    val surah = Json.decodeFromString<Surah>(surahJson)
    println("Surah Object: $surah")

    println("\n> Total number of Ayat: ${SurahRepository.totalAyat}")
    println("> Number of surahs by type: ${SurahRepository.surahCountByType}")
    println("> Aya count by surah type: ${SurahRepository.ayaCountByType}")


	println("\n> Surahs having more than 200 Ayat:")
    SurahRepository.getSurahs(200).forEachIndexed{ i, surah -> println("${i+1}: " + surah) }

	println("\n> First 5 Medinan Surahs:")
    SurahRepository.getSurahs("Medinan").take(5).forEach(::println)
		
	println("\n> Longest Surah: ${SurahRepository.getLongestSurah()}")
}