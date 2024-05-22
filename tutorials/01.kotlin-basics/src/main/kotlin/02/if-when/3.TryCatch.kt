package controlFlow

fun tryParseInt(number: String) =
    // Try-catch expression
    try {
        Integer.parseInt(number)
    } catch (e: NumberFormatException) {
        null
    }

fun main() {
    val num = tryParseInt("Test")
    println(num)
}