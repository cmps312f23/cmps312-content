package qu.lingosnacks.viewmodel

import android.app.Application
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.ResourceTypeEnum
import qu.lingosnacks.entity.Review
import qu.lingosnacks.entity.Score
import qu.lingosnacks.repository.PackageRepository
import qu.lingosnacks.utils.getRandomId

class PackageViewModel(application: Application) : AndroidViewModel(application) {
    private val packageRepository = PackageRepository(application)
    var packages = emptyList<LearningPackage>().toMutableStateList()

    init {
        // Init packages
        getPackages()
    }

    fun getPackages() {
        packages = packageRepository.getPackages().toMutableStateList()
    }

    fun getPackages(searchText: String) {
        packages = packageRepository.getPackages(searchText).toMutableStateList()
    }

    //fun getPackage(packageId: String) = packageRepository.getPackage(packageId)
    fun getPackage(packageId: String) : LearningPackage {
        //packageRepository.getPackage(packageId)
        return packages.find { it.packageId == packageId } ?: LearningPackage()
    }

    fun deleteOnlinePackage(packageId: String) {
        packageRepository.deleteOnlinePackage(packageId)
        packages.removeIf { it.packageId == packageId }
    }

    fun deleteLocalPackage(packageId: String) {
        packageRepository.deleteLocalPackage(packageId)
        packages.removeIf { it.packageId == packageId }
    }

    // Update package if exists otherwise add it
    fun upsertPackage(learningPackage: LearningPackage) {
        //var foundAt = packages.indexOfFirst { pack -> pack.packageId == learningPackage.packageId }
        //if (foundAt >= 0) {
        if (learningPackage.packageId != "0") {
            packageRepository.updatePackage(learningPackage)

            // A trick to trigger UI recomposition after update
            packages = packages.map { lp ->
                if (lp.packageId == learningPackage.packageId) learningPackage
                else lp
            }.toMutableStateList()

            // This does NOT trigger UI recomposition
            //packages[foundAt] = learningPackage
        } else {
            learningPackage.packageId = getRandomId()
            packageRepository.addPackage(learningPackage)
            packages.add(learningPackage)
            println(">> Debug: upsertPackage: ${packages.size}")
        }
    }

    fun downloadPackage(packageId: String) = packageRepository.downloadPackage(packageId)

    fun getReviews(packageId: String) = packageRepository.getReviews(packageId)
    fun getReview(packageId: String, doneBy: String) = packageRepository.getReview(packageId, doneBy)
    fun getPackageAvgRating(packageId: String) = packageRepository.getPackageAvgRating(packageId)
    // Update review if exists otherwise add it
    fun upsertReview(review: Review) = packageRepository.upsertReview(review)

    fun getScores(uid: String) = packageRepository.getScores(uid)
    fun getScoresSummary(uid: String) = packageRepository.getScoresSummary(uid)
    fun addScore(score: Score) = packageRepository.addScore(score)
    //fun getLeaderBoard() = packageRepository.getLeaderBoard()

    // Hardcoded for simplicity. In real app data should come from a DB
    fun getLevels() = listOf("Beginner", "Intermediate", "Advanced")
    fun getLanguages() = listOf("English", "Arabic", "French", "Spanish", "Italian")
    fun getCategories() = listOf("Humanities", "Science and Technology", "Health and Fitness", "Business", "Politics", "Travel")
    fun getResourceTypes() = listOf(ResourceTypeEnum.Photo.toString(),
                                    ResourceTypeEnum.Video.toString(),
                                    ResourceTypeEnum.Website.toString())

    fun initFirestoreDB() {
        packageRepository.initFirestoreDB()
    }
}