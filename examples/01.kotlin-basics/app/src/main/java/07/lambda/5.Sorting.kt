package `07`.lambda

fun main() {
    val names = listOf("Abderahame", "Abdelkarim", "Ali", "Sarah", "Samira", "Farida")
    var sorted = names.sortedBy { name -> name.length }
    //Better version - short form
    println(">Sorted by length:")
    sorted = names.sortedBy { it.length }
    println(sorted)

    println("\n>Sorted by length and then alphabetically:")
    //Sort strings by length (shortest to longest) and then alphabetically
    sorted = names.sortedWith( compareBy( { it.length }, { it }) )
    println(sorted)

}