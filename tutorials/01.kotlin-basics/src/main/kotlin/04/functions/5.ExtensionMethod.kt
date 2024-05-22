package functions

fun isIntEven(num: Int) = num % 2 == 0

// Better way of doing it to use an extension method extending Int class
fun Int.isEven() = this % 2 == 0

fun Char.isLatinUppercase() = this in 'A'..'Z'

fun String.lastChar() = this[length - 1]
val String.lastChar get() = get(length - 1)

fun String.print() = println(this)

fun main() {
    val num = 10
    println("Is $num even: ${isIntEven(num)}")
    println("Is $num even: ${num.isEven()}")

    println("Doha".lastChar())
    println("Qatar".lastChar)

    "Kotlin is cool".print()

    println("Is 'a' Latin Uppercase: ${'a'.isLatinUppercase()}")
}
