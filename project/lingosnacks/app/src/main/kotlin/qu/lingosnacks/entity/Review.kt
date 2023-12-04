package qu.lingosnacks.entity

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    var reviewId: String = "",
    val packageId: String,
    var comment: String,
    val doneOn: String,
    val doneBy: String,
    var rating: Double
) {
    constructor(): this(reviewId = "",
                    packageId= "",
                    comment = "",
                    doneOn = "",
                    doneBy = "",
                    rating = 0.0)
}