package qu.lingosnacks.repository

import android.content.Context
import qu.lingosnacks.entity.Definition
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Resource
import qu.lingosnacks.entity.Sentence
import qu.lingosnacks.entity.Word
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.json.Json

object PackagesRepository {
    var packages = mutableListOf<LearningPackage>()

    fun initPackages(context: Context): List<LearningPackage> {
        if (packages.isEmpty()) {
            val packageJsonString =
                context.assets.open("packages.json").bufferedReader().use { it.readText() }
            packages = Json { ignoreUnknownKeys = true }.decodeFromString(packageJsonString)
        }
        return packages
    }

    fun generatePackageId(): Int {
        return (packages.asSequence().map { it.packageId }.maxOrNull() ?: 0) + 1
    }

    fun addPackage(
        author: String,
        category: String,
        description: String,
        iconUrl: String?,
        language: String,
        level: String,
        title: String,
        words: MutableList<Word> = mutableListOf<Word>()
    ): Boolean {
        return packages.add(
            LearningPackage(
                generatePackageId(),
                author,
                category,
                description,
                iconUrl,
                language = language,
                level = level,
                title = title,
                words = words
            )
        )
    }

    fun addPackage(packageObj: LearningPackage): Boolean {
        return addPackage(
            packageObj.author,
            packageObj.category,
            packageObj.description,
            packageObj.iconUrl,
            packageObj.language,
            packageObj.level,
            packageObj.title,
            packageObj.words
        )
    }

    fun updatePackage(packageObj: LearningPackage): Boolean {
        val index = packages.indexOfFirst { it.packageId == packageObj.packageId }
        if (index == -1) return false
        packages[index] = packageObj.copy(
            version = packageObj.version++, lastUpdatedDate = Clock.System.todayIn(
                TimeZone.currentSystemDefault()
            )
        )
        return true
    }

    fun deletePackage(packageObj: LearningPackage): Boolean {
        return packages.remove(packageObj)
    }

    //Delete package only if owned by user
    fun verifiedDeletePackage(packageObj: LearningPackage, userEmail: String): Boolean {
        if (packageObj.author != userEmail) return false
        return deletePackage(packageObj)
    }

    fun getPackageById(id: Int): LearningPackage? {
        return packages.find { it.packageId == id }
    }

    fun getPackagesByAuthor(authorEmail: String): List<LearningPackage> {
        return packages.filter { it.author == authorEmail }
    }

    fun getTotalPackagesByAuthor(authorEmail: String): Int {
        return getPackagesByAuthor(authorEmail).size
    }

    fun getMainLanguage(authorEmail: String): String {
        val packagesByAuthor = getPackagesByAuthor(authorEmail)
        if (packagesByAuthor.isEmpty()) return "-"
        val languagesRepeat = packagesByAuthor.map { it.language }
        val uniqueLang = languagesRepeat.toSet()
        val langCount = uniqueLang.map { lang -> lang to languagesRepeat.count { it == lang } }
            .sortedBy { it.second }
        return langCount.last().first
    }

    fun getUserRating(authorEmail: String): Double {
        val packagesByAuthor = getPackagesByAuthor(authorEmail)
        if (packagesByAuthor.isEmpty()) return 0.0
        return packagesByAuthor.map { it.avgRating }.average()
    }

    fun getWordTotals(word: String, packageId: Int): Map<String, Int> {
        val packageObj = getPackageById(packageId)
            ?: return mapOf(
                "definitions" to 0, "sentences" to 0,
                "def" to 0, "sent" to 0
            )
        return packageObj.getWordTotals(word)
    }

    fun searchPackages(searchText: String): List<LearningPackage> {
        return packages.filter {
            it.title.contains(searchText, ignoreCase = true) ||
                    it.description.contains(searchText, ignoreCase = true) ||
                    it.keywords.contains(searchText, ignoreCase = true) ||
                    /*it.keywords.any { keyword ->
                        keyword.contains(
                            searchText,
                            ignoreCase = true
                        )
                    } ||*/
                    it.level.contains(searchText, ignoreCase = true)
        }
    }

    //Add, update, delete words
    fun addWord(packageId: Int, word: Word): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        if (packageObj.words.any { it.text == word.text }) return false
        return packageObj.words.add(word)
    }

    fun updateWord(packageId: Int, oldWord: Word, newWord: Word): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == oldWord.text }
        if (index == -1) return false
        packageObj.words[index] = newWord
        return true
    }

    fun deleteWord(packageId: Int, word: Word): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        return packageObj.words.remove(word)
    }

    fun deleteWord(packageId: Int, word: String): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        packageObj.words.removeAt(index)
        return true
    }

    //Add, update, delete definitions
    fun addDefinition(packageId: Int, word: String, definition: Definition): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        return packageObj.words[index].definitions.add(definition)
    }

    fun addDefinition(packageId: Int, word: Word, definition: Definition): Boolean {
        return addDefinition(packageId, word.text, definition)
    }

    fun updateDefinition(
        packageId: Int,
        word: String,
        oldDefinition: Definition,
        newDefinition: Definition
    ): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        val defIndex =
            packageObj.words[index].definitions.indexOfFirst { it.text == oldDefinition.text }
        if (defIndex == -1) return false
        packageObj.words[index].definitions[defIndex] = newDefinition
        return true
    }

    fun updateDefinition(
        packageId: Int,
        word: Word,
        oldDefinition: Definition,
        newDefinition: Definition
    ): Boolean {
        return updateDefinition(packageId, word.text, oldDefinition, newDefinition)
    }

    fun deleteDefinition(packageId: Int, word: String, definition: Definition): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        return packageObj.words[index].definitions.remove(definition)
    }

    fun deleteDefinition(packageId: Int, word: Word, definition: Definition): Boolean {
        return deleteDefinition(packageId, word.text, definition)
    }

    //Add, update, delete sentences
    fun addSentence(packageId: Int, word: String, sentence: Sentence): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        return packageObj.words[index].sentences.add(sentence)
    }

    fun addSentence(packageId: Int, word: Word, sentence: Sentence): Boolean {
        return addSentence(packageId, word.text, sentence)
    }

    fun updateSentence(
        packageId: Int,
        word: String,
        oldSentence: Sentence,
        newSentence: Sentence
    ): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        val sentIndex =
            packageObj.words[index].sentences.indexOfFirst { it.text == oldSentence.text }
        if (sentIndex == -1) return false
        packageObj.words[index].sentences[sentIndex] = newSentence
        return true
    }

    fun updateSentence(
        packageId: Int,
        word: Word,
        oldSentence: Sentence,
        newSentence: Sentence
    ): Boolean {
        return updateSentence(packageId, word.text, oldSentence, newSentence)
    }

    fun deleteSentence(packageId: Int, word: String, sentence: Sentence): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val index = packageObj.words.indexOfFirst { it.text == word }
        if (index == -1) return false
        return packageObj.words[index].sentences.remove(sentence)
    }

    fun deleteSentence(packageId: Int, word: Word, sentence: Sentence): Boolean {
        return deleteSentence(packageId, word.text, sentence)
    }

    //Add, update, delete resources
    fun addResource(packageId: Int, word: String, sentence: String, resource: Resource): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val wordIndex = packageObj.words.indexOfFirst { it.text == word }
        if (wordIndex == -1) return false
        val sentIndex = packageObj.words[wordIndex].sentences.indexOfFirst { it.text == sentence }
        if (sentIndex == -1) return false
        return packageObj.words[wordIndex].sentences[sentIndex].resources.add(resource)
    }

    fun addResource(packageId: Int, word: Word, sentence: Sentence, resource: Resource): Boolean {
        return addResource(packageId, word.text, sentence.text, resource)
    }

    fun updateResource(
        packageId: Int,
        word: String,
        sentence: String,
        oldResource: Resource,
        newResource: Resource
    ): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val wordIndex = packageObj.words.indexOfFirst { it.text == word }
        if (wordIndex == -1) return false
        val sentIndex = packageObj.words[wordIndex].sentences.indexOfFirst { it.text == sentence }
        if (sentIndex == -1) return false
        val resIndex =
            packageObj.words[wordIndex].sentences[sentIndex].resources.indexOfFirst { it.title == oldResource.title }
        packageObj.words[wordIndex].sentences[sentIndex].resources[resIndex] = newResource
        return true
    }

    fun updateResource(
        packageId: Int,
        word: Word,
        sentence: Sentence,
        oldResource: Resource,
        newResource: Resource
    ): Boolean {
        return updateResource(packageId, word.text, sentence.text, oldResource, newResource)
    }

    fun deleteResource(
        packageId: Int,
        word: String,
        sentence: String,
        resource: Resource
    ): Boolean {
        val packageObj = getPackageById(packageId) ?: return false
        val wordIndex = packageObj.words.indexOfFirst { it.text == word }
        if (wordIndex == -1) return false
        val sentIndex = packageObj.words[wordIndex].sentences.indexOfFirst { it.text == sentence }
        if (sentIndex == -1) return false
        return packageObj.words[wordIndex].sentences[sentIndex].resources.remove(resource)
    }

    fun deleteResource(
        packageId: Int,
        word: Word,
        sentence: Sentence,
        resource: Resource
    ): Boolean {
        return deleteResource(packageId, word.text, sentence.text, resource)
    }

    //Uniqueness checks
    fun isUniquePackage(title: String, description: String, packageId: Int): Boolean {
        return packages.filterNot { it.packageId == packageId }.none {
            it.title.equals(title.trim(), true) || it.description.equals(description.trim(), true)
        }
    }

    fun isUniqueWord(word: String, words: List<Word>): Boolean {
        return words.none { it.text.equals(word.trim(), true) }
    }

    fun isUniqueSentence(sentence: String, sentences: List<Sentence>): Boolean {
        return sentences.none { it.text.equals(sentence.trim(), true) }
    }

    fun isUniqueDefinition(definition: String, definitions: List<Definition>): Boolean {
        return definitions.none { it.text.equals(definition.trim(), true) }
    }

    fun isUniqueResource(title: String, url: String, resources: List<Resource>): Boolean {
        return resources.none {
            it.title.equals(title.trim(), true) || it.url.equals(url.trim(), true)
        }
    }
}