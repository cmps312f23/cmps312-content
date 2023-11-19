package qu.lingosnacks.entity

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    val role: String,
    var photoUri: String = ""
) {
    val uid: String = UUID.randomUUID().toString().split("-")[0]

    val fullName: String
        get() = "$firstName $lastName"

    override fun toString()
            = "${firstName.trim()} ${lastName.trim()} - ${email.trim()}".trim()
}