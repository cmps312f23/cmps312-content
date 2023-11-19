package qu.lingosnacks.repository

import android.content.Context
import kotlinx.serialization.json.Json
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Rating
import qu.lingosnacks.entity.Score

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

data class ScoreSummary(val gameName: String, val score: Int, val outOf: Int) {
    override fun toString() : String {
        val scorePercentage = (score.toDouble() / outOf * 100.0).round(2)
        return "$gameName -> $score / $outOf ($scorePercentage%)"
    }
}
data class LeaderBoardMember(val rank: Int, val uid: String, val displayName: String,
                             val photoUri : String,
                             val gameName: String, val score: Int, val outOf: Int)

class PackageRepository(val context: Context) {
    // ToDo: Implement all PackageRepository methods to read/write from the online/local database
    fun getPackages() : List<LearningPackage> {
        val data = context.assets.open("packages.json")
            .bufferedReader().use { it.readText() }
        val packages = Json{ ignoreUnknownKeys = true }.decodeFromString<List<LearningPackage>>(data)
        /*packages.forEach {
            it.keywords = it.words.joinToString(", ") { it.text }
        }*/
        return packages
    }

    fun getPackages(searchText: String) : List<LearningPackage> {
        var packages = getPackages()

        if (searchText.isEmpty())
            return packages

        packages = packages.filter {
            it.keywords.contains(searchText, true) || //any { kw -> kw.contains(searchText, ignoreCase = true) } ||
            it.description.contains(searchText, true)
        }
        return packages
    }

    // ToDo: Add/update the learning package to Firestore & upload the associated media files to Firebase Cloud Storage
    fun addPackage(learningPackage: LearningPackage) {}
    fun updatePackage(learningPackage: LearningPackage) {}

    // ToDo: Delete package from Firestore and its associated resources from Cloud Storage
    fun deleteOnlinePackage(learningPackage: LearningPackage) {}

    // ToDo: Download package from Firestore and its associated resources from Cloud Storage so that the package can be used offline
    fun downloadPackage(packageId: String) {}

    // ToDo: Delete package from local database and its associated resource files
    fun deleteLocalPackage(learningPackage: LearningPackage) {}

    fun getRatings(packageId: String) : List<Rating> {
        val data = context.assets.open("ratings.json")
            .bufferedReader().use { it.readText() }
        val ratings = Json{ ignoreUnknownKeys = true }.decodeFromString<List<Rating>>(data)
        //ToDo: remove the toString
        return ratings.filter { it.packageId.toString() == packageId }
    }

    // ToDO: Whenever a new rating is added then recompute & update the package avgRating and numRatings
    // this is typically done to improve performance and avoid computing the rating upon request
    /*
        // Compute new number of ratings
        val newNumRatings = numRatings + 1

        // Compute new average rating
        val oldRatingTotal = avgRating * numRatings
        val newAvgRating = (oldRatingTotal + rating) / newNumRatings
     */
    fun addRating(rating: Rating) {
        println(">> Debug: PackageRepository.addRating: $rating")
    }

    // ToDo: replace this example data with database query to get scores summary by uid
    fun getScores(uid: String): List<ScoreSummary> =
        listOf(
            ScoreSummary("Unscramble Sentence", 90, 120),
            ScoreSummary("Match Definition", 55, 80)
        )

    fun addScore(score: Score) {
        println(">> Debug: PackageRepository.addScore: $score")
    }

    // ToDo: replace this example data with database query to get leader board members
    fun getLeaderBoard(): List<LeaderBoardMember> =
        listOf(
            LeaderBoardMember(1, "123", "Sponge Bob",
                "https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/SpongeBob_SquarePants_character.svg/440px-SpongeBob_SquarePants_character.svg.png",
                "Unscramble Sentence", 90, 120),

            LeaderBoardMember(2, "234", "Bart Simpson",
                "https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png",
                "Unscramble Sentence", 60, 100),

            LeaderBoardMember(3, "345", "Bugs Bunny",
                "https://upload.wikimedia.org/wikipedia/en/thumb/1/17/Bugs_Bunny.svg/360px-Bugs_Bunny.svg.png",
                "Unscramble Sentence", 50, 120),

            LeaderBoardMember(1, "123", "Sponge Bob",
                "https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/SpongeBob_SquarePants_character.svg/440px-SpongeBob_SquarePants_character.svg.png",
                "Match Definition", 100, 120),

            LeaderBoardMember(2, "234", "Bart Simpson",
                "https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png",
                "Match Definition", 70, 100),

            LeaderBoardMember(3, "345", "Bugs Bunny",
                "https://upload.wikimedia.org/wikipedia/en/thumb/1/17/Bugs_Bunny.svg/360px-Bugs_Bunny.svg.png",
                "Match Definition", 60, 120)
        )

    // ToDo initialize Firestore db with data from packages.json and users.json
    fun initFirestoreDB() {}
}