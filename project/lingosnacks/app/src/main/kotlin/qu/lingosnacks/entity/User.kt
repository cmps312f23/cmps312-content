package qu.lingosnacks.entity

import kotlinx.serialization.Serializable
import qu.lingosnacks.utils.getRandomId

@Serializable
data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    val role: String,
    var photoUrl: String = ""
) {
    constructor(uid : String) : this(firstName = "",
        lastName = "", email = "",
        password = "", role = "") {
        this.uid = uid
    }

    var uid: String = getRandomId()

    val fullName: String
        get() = "$firstName $lastName"

    override fun toString()
            = "${firstName.trim()} ${lastName.trim()} - ${email.trim()}".trim()
}