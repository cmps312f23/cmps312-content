package collections

fun main() {
    val languages = mapOf(
        1 to "Python",
        2 to "Kotlin",
        3 to "Java"
    )

    for ((key, value) in languages) {
        println("$key => $value")
    }

    val capitals = hashMapOf<String, String>()
    capitals["Qatar"] = "Doha"
    capitals["India"] = "New Delhi"
    capitals["United States"] = "Washington"
    capitals["England"] = "London"
    capitals["Australia"] = "Canberra"

    for ((country, capital) in capitals) {
        println("Capital of $country is $capital")
    }
}