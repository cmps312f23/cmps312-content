package functions

// functions with varargs parameters
fun varargExample(vararg names: Int) {
    println("Argument has ${names.size} elements")
}

fun max(vararg numbers: Int) = numbers.maxOrNull()

fun main() {
    varargExample() // => Argument has 0 elements
    varargExample(1) // => Argument has 1 elements
    varargExample(1, 2, 3) // => Argument has 3 elements

    println(max(1, 2))
    println(max(7, 3, 11))

    val values = intArrayOf(1, 22, 4)
    println(max(4, 5, *values, 8))
}
