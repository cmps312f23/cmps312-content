package qu.lingosnacks.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable

@Serializable
data class LearningPackage(
    var packageId: String,
    var title: String,
    var description: String,
    var category: String,
    var level: String,
    var language: String,
    var iconUrl: String,
    val author: String,
    var lastUpdatedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    var version: Int = 1,
    // ToDo: Do NOT store this property in the online DB
    // ToDo: When you get packages, you should set it to true for packages that were downloaded to the local DB
    var isDownloaded : Boolean = false,
    //var avgRating: Double = 0.0,
    //internal var numRatings: Int = 0,
    var words: MutableList<Word> = mutableListOf(),
) {
    constructor() : this(packageId = "0",
        title="", description = "",
        category = "", level = "",
        language = "", iconUrl = "",
        author = "",
    )

    // ToDo: not clear why this is needed
    fun getWordTotals(word: String): Map<String, Int> {
        val wordObj = words.find { it.text == word }
        return wordObj?.totals ?: mapOf(
            "definitions" to 0, "sentences" to 0,
            "def" to 0, "sent" to 0
        )
    }
}

fun LearningPackage.isAuthor(user: User?) = user?.email.equals(this.author, true)