package qu.lingosnacks.entity

import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    var text: String,
    var source: String
)