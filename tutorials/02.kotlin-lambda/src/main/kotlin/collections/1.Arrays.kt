package collections

fun main() {
    // Use Lists to be able to easily add/remove elements. Arrays offer limited capabilities.
    val colors = arrayOf("Red", "Green", "Blue")
    colors[2] = "Pink"
    println("> Colors array size: ${colors.size}")
    colors.forEach { println(it) }

    val nums = arrayOf(2, 3, 4)
    println("> Nums array size: ${nums.size}")
    nums.forEach { println(it) }
}