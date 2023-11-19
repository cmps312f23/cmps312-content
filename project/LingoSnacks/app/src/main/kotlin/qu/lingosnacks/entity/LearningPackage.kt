package qu.lingosnacks.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable

@Serializable
data class LearningPackage(
    //ToDo: make this property type String
    val packageId: Int,
    val author: String,
    var category: String,
    var description: String,
    var iconUrl: String? = null,
    var keywords: String = "", //MutableList<String> = mutableListOf(),
    var language: String,
    var lastUpdatedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    var level: String,
    var title: String,
    var version: Int = 1,
    var avgRating: Double = 0.0,
    internal var numRatings: Int = 0,
    var words: MutableList<Word> = mutableListOf(),
    //ToDo: consider removing and keep it separate from the package
    var ratings: MutableList<Rating> = mutableListOf(),
) {
    constructor() : this(0,author = "",
        category = "", description = "",
        iconUrl = "", keywords = "", language = "",
        level = "", title="")
    /*val avgRating: Float
        get() {
            if (ratings.isEmpty()) return 0f
            return ratings.map { it.rating }.average().toFloat()
        }*/

    fun getRatingCount(): Int {
        return ratings.size
    }

    fun getRatingByUser(userId: String): Rating? {
        return ratings.find { it.doneBy == userId }
    }

    // ToDo: not clear why this is needed
    fun getWordTotals(word: String): Map<String, Int> {
        val wordObj = words.find { it.text == word }
        return wordObj?.totals ?: mapOf(
            "definitions" to 0, "sentences" to 0,
            "def" to 0, "sent" to 0
        )
    }

    fun checkAuthorship(userId: String): Boolean {
        return author == userId
    }

    fun isOwner(userInfo: UserInfo) = userInfo.email.equals(author, true)
    fun isTeacher(userInfo: UserInfo) = userInfo.role.equals("Teacher", true)
}
