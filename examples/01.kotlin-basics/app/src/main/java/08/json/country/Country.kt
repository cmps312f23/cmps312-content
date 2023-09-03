package json.country

import kotlinx.serialization.Serializable

@Serializable
data class Country (
    val code: String,
    val name: String,
    val capital: String,
    val continent: String,
    val region: String,
    val population: Int,
    val area: Long = 0
)