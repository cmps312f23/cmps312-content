package qu.lingosnacks.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val packageId: Int,
    var comment: String,
    val doneOn: LocalDate,
    val doneBy: String,
    var rating: Double
)