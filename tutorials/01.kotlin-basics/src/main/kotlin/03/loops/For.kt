package `03`.loops

fun main() {

    // CASE 1: Looping from 1 to 10
    println("CASE 1")
    for (item in 1..10) {
        println(item)
    }

    // CASE 2: Looping the items in a list
    println(" ")
    println("CASE 2")
    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    val names = listOf("Sara", "Fatima", "Ali")

    for (name in names) {
        println(name)
    }

    for ( (index, value) in names.withIndex()) {
        println("$index -> $value")
    }

    println("------")
    for (index in names.indices) {
        println("$index -- ${names[index]}")
    }

    // CASE 3: Looping the items with the index
    println(" ")
    println("CASE 3")
    for ((index, item) in list.withIndex()) {
        println("At $index --> $item")
    }

    for ((index, name) in names.withIndex()) {
        println("At $index --> $name")
    }
}