package shopapp.entity

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @DocumentId
    val id: String = "",
    val name: String,
    val icon: String,
    val categoryId: String = "",
    val category: String? = null) {
    // Required by Firebase deserializer otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "", "")

    constructor(name: String, icon: String, categoryId: String)
            : this("", name, icon, categoryId)

    override fun toString(): String {
        return "$name $icon"
    }
}