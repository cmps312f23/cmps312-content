package shopapp.entity

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @DocumentId
    val id: String = "",
    val name: String) {

    // Required by Firebase deserializer
    // otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "")

    override fun toString(): String {
        return name
    }
}