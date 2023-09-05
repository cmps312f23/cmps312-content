package functions

fun displayBorder() {
    displayBorder('*', 20)
}

fun displayBorder(character: Char) {
    displayBorder(character, 20)
}

fun displayBorder(character: Char,  length:Int) {
    for (i in 1..length) {
        print(character)
    }
}

// Use default parameters instead of function overloading
/*fun displayBorder(character: Char = '*', length: Int = 20) {
    for (i in 1..length) {
        print(character)
    }
}*/

fun main() {
    println("Output when no argument is passed:")
    displayBorder()

    println("\n\n'=' is used as a first argument.")
    displayBorder('=')

    println("\n\n'=' is used as a first argument.")
    println("5 is used as a second argument.")
    displayBorder('=', 5)
}