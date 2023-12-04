package qu.lingosnacks.entity

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    var text: String,
    var definitions: MutableList<Definition> = mutableListOf(),
    var sentences: MutableList<Sentence> = mutableListOf(),
) {
    private val definitionsCount: Int
        get() {
            return definitions.size
        }

    private val sentencesCount: Int
        get() {
            return sentences.size
        }

    //ToDo: not sure why this is needed
    val totals: Map<String, Int>
        get() {
            return mapOf(
                "definitions" to definitionsCount, "sentences" to sentencesCount,
                "def" to definitionsCount, "sent" to sentencesCount
            )
        }

    fun addResource(sentence: Sentence, resource: Resource): Boolean {
        val sentIndex = sentences.indexOf(sentence)
        if (sentIndex == -1) return false
        return sentences[sentIndex].addResource(resource)
    }

    fun addResource(sentenceText: String, resource: Resource): Boolean {
        val sentIndex = sentences.indexOfFirst { it.text == sentenceText }
        if (sentIndex == -1) return false
        return sentences[sentIndex].addResource(resource)
    }
}
