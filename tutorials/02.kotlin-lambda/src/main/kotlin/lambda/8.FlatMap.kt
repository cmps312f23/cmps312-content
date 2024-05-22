package lambda

class Book(
    val title: String,
    val authors: List<String>
)

fun main() {
    val books = listOf(
        Book("Head First Kotlin", listOf("Dawn Griffiths", "David Griffiths")),
        Book("Kotlin in Action", listOf("Dmitry Jemerov", "Svetlana Isakova"))
    )
    // Do a map and flatten the results into 1 list
    val authors = books.flatMap { it.authors }
    println(authors)
    // Transform a list to a string
    println(authors.joinToString())
}
