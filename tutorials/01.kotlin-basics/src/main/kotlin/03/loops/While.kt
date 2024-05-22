package `03`.loops

fun main() {

    // CASE 1: 'while' loop
    println("CASE 1")
    var volume = 20
    while (volume > 0) {
        println("Audio is ON")
        volume--
    }

    // CASE 2: 'do while' loop
    println(" ")
    println("CASE 2")
    val isVisible = true
    do {
        println("I execute at least once")
    } while (!isVisible)

}