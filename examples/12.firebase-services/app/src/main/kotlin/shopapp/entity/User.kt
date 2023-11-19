package shopapp.entity

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class User (
    @DocumentId
    var uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    // Marks property as excluded from saving to Firestore
    @get:Exclude val password: String,
    val role: String) {
    // Required by Firebase deserializer otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "", "", "",  "", "")
    constructor(uid: String, displayName: String, email: String): this(uid, displayName, "", email,  "", "")

    override fun toString()
            = "${firstName.trim()} - ${email.trim()}" //${lastName.trim()}
}