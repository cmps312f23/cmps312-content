package lambda

data class Conference(val name: String,
                 var city: String,
                 var fee : Double) {
}
fun main() {
    val conference = Conference("Kotlin Conf.", "Istanbul", 300.0)

    // Version 1 👎🏻 - Change the conference city and fee then print it
    conference.city = "Doha"
    conference.fee = 200.0
    println(conference)

    // Version 2 👎🏻 - Change the conference city and fee then print it
    // .with changes the object  but DOES returns Unit (which is equivalent to void in Java)
    // .also execute some processing on the object and returns it
    with(conference) {
        city = "Kabul"
        fee = 100.0
    }.also { println(it) }

    // conference object did change using with
    println(conference)

    // Version 3 ** Best 👍 ** - Change the conference city and fee then print it
    // .apply changes the object and returns it
    // .also execute some processing on the object and returns it
    conference.apply {
        city = "Doha"
        fee = 200.0
    }.also { println(it) }

    // conference object did change using apply
    println(conference)
}
