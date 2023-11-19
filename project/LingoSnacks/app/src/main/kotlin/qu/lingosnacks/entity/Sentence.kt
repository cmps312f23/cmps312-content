package qu.lingosnacks.entity

import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    var text: String,
    var resources: MutableList<Resource> = mutableListOf()
) {
    fun addResource(resource: Resource): Boolean {
        return resources.add(resource)
    }

    fun removeResource(resource: Resource): Boolean {
        return resources.remove(resource)
    }

    fun removeResourceByTitle(title: String): Boolean {
        return resources.removeIf { it.title == title }
    }

    fun removeResourceByURL(url: String): Boolean {
        return resources.removeIf { it.url == url }
    }
}
