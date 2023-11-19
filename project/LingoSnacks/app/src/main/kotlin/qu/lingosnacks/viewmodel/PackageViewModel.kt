package qu.lingosnacks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Rating
import qu.lingosnacks.entity.Score
import qu.lingosnacks.repository.PackageRepository

class PackageViewModel(application: Application) : AndroidViewModel(application) {
    private val packageRepository = PackageRepository(application)
    private var _packages = MutableStateFlow(emptyList<LearningPackage>().toMutableList())
    var selectedPackage: LearningPackage? = null

    val packages = _packages as StateFlow<List<LearningPackage>>

    init {
        // Init packages
        getPackages()
    }

    fun getPackages() {
        _packages.value = packageRepository.getPackages().toMutableList()
    }

    fun getPackages(searchText: String) {
        _packages.value = packageRepository.getPackages(searchText).toMutableList()
    }

    fun deleteOnlinePackage() {
        packageRepository.deleteOnlinePackage(selectedPackage!!)
        _packages.value?.let {
            it.remove(selectedPackage!!)
            _packages.value = it
            selectedPackage = null
        }
    }

    fun deleteLocalPackage() {
        packageRepository.deleteLocalPackage(selectedPackage!!)
        _packages.value?.let {
            it.remove(selectedPackage!!)
            _packages.value = it
            selectedPackage = null
        }
    }

    // Add package if exists otherwise add it
    fun upsertPackage(learningPackage: LearningPackage) {
        _packages.value?.let {
            // If item exists just increase the quantity
            var foundAt = it.indexOfFirst { pack -> pack.packageId == learningPackage.packageId }
            if (foundAt >= 0) {
                packageRepository.updatePackage(learningPackage)
                it[foundAt] = learningPackage
                // This is needed to notify the observers
                _packages.value = it
            } else {
                packageRepository.addPackage(learningPackage)
                // This is needed to notify the observers
                it.add(learningPackage)
                _packages.value = it
                println(">> Debug: upsertPackage: ${it.size}")
            }
        }
    }

    fun downloadPackage() = packageRepository.downloadPackage(selectedPackage?.packageId!!.toString())

    fun getRatings() = packageRepository.getRatings(selectedPackage?.packageId!!.toString())
    fun addRating(rating: Rating) = packageRepository.addRating(rating)

    fun getScores(uid: String) = packageRepository.getScores(uid)
    fun addScore(score: Score) = packageRepository.addScore(score)
    fun getLeaderBoard() = packageRepository.getLeaderBoard()

    fun initFirestoreDB() {
        packageRepository.initFirestoreDB()
    }
}