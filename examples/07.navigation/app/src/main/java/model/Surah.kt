package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Verse(val id : Int, val text: String)

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    @SerialName("transliteration")
    val englishName : String,
    @SerialName("total_verses")
    val ayaCount : Int,
    val type: String = "Meccan",
    val verses: List<Verse>
)