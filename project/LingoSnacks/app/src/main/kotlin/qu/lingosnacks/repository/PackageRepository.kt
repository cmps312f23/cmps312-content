package qu.lingosnacks.repository

import android.content.Context
import kotlinx.serialization.json.Json
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Review
import qu.lingosnacks.entity.Score

class PackageRepository(val context: Context) {
    // ToDo: Implement all PackageRepository methods to read/write from the online/local database
    fun getPackages() : List<LearningPackage> {
        val data = context.assets.open("packages.json")
            .bufferedReader().use { it.readText() }
        var packages : List<LearningPackage> = listOf()
        try {
            packages =
                Json { ignoreUnknownKeys = true }.decodeFromString<List<LearningPackage>>(data)
        } catch (e: Exception) {
            println(e.message)
        }
        return packages
    }

    fun getPackages(searchText: String) : List<LearningPackage> {
        var packages = getPackages()

        if (searchText.isEmpty())
            return packages

        packages = packages.filter {
            it.words.any { w -> w.text.contains(searchText, ignoreCase = true) } ||
            it.description.contains(searchText, true)
        }
        return packages
    }

    fun getPackage(packageId: String): LearningPackage? {
        val packages = getPackages()
        return packages.find {it.packageId == packageId }
    }

    // ToDo: Add/update the learning package to Firestore & upload the associated media files to Firebase Cloud Storage
    fun addPackage(learningPackage: LearningPackage) {}
    fun updatePackage(learningPackage: LearningPackage) {}

    // ToDo: Delete package from Firestore and its associated resources from Cloud Storage
    fun deleteOnlinePackage(packageId: String) {
    }

    /* ToDo: Download package from Firestore to a local SqlLite database
       and download its associated resources from Cloud Storage
       so that the package can be used offline */
    fun downloadPackage(packageId: String) {}

    // ToDo: Delete package from the local database and its associated resource files
    fun deleteLocalPackage(packageId: String) {}

    fun getReviews(packageId: String) : List<Review> {
        val data = context.assets.open("reviews.json")
            .bufferedReader().use { it.readText() }
        val reviews = Json{ ignoreUnknownKeys = true }.decodeFromString<List<Review>>(data)
        return reviews.filter { it.packageId == packageId }
    }

    fun getReview(packageId: String, doneBy: String) : Review? {
        val reviews = getReviews(packageId)
        return reviews.firstOrNull { it.doneBy == doneBy }
    }

    fun getPackageAvgRating(packageId: String) : Double {
        val reviews = getReviews(packageId)
        return if (reviews.isNotEmpty()) {
            val avgRating = reviews.map { it.rating }.average()
            val reviewAvgRounded = String.format("%.1f", avgRating).toDouble()
            reviewAvgRounded
        } else {
            0.0
        }
    }

    // ToDo: Update review if exists otherwise add it
    fun upsertReview(review: Review) {
        println(">> Debug: PackageRepository.upsertReview: $review")
    }

    fun getScores(uid: String): List<Score> {
        val data = context.assets.open("scores.json")
            .bufferedReader().use { it.readText() }
        val reviews = Json{ ignoreUnknownKeys = true }.decodeFromString<List<Score>>(data)
        //ToDo: remove the toString
        return reviews.filter { it.uid == uid }
    }

    // ToDo: replace this example data with database query to get scores summary by uid
    fun getScoresSummary(uid: String): Map<String, Int> {
        val scores = getScores(uid)
        val scoresSummary = scores.groupBy { it.gameName }.map {
            Pair(it.key, it.value.map { s-> s.score }.average().toInt())
        }.toMap()

        return scoresSummary
        /*listOf(
            ScoreSummary("Unscramble Sentence", 90, 100),
            ScoreSummary("Match Definition", 55, 100)
        )*/
    }

    fun addScore(score: Score) {
        println(">> Debug: PackageRepository.addScore: $score")
    }

    // ToDo: replace this example data with database query to get leader board members
    /*fun getLeaderBoard(): List<LeaderBoardMember> =
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
        )*/

    // ToDo initialize Firestore db with data from packages.json and users.json
    fun initFirestoreDB() {}
}