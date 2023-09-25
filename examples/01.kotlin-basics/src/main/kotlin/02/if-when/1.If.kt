package controlFlow

fun isOpen(hour: Int) : Boolean {
    val open = 9
    val closed = 22
    return hour in open..closed
}

fun main() {
    println("> Is mall open: ${isOpen(10)}")

    val age = 20

    // Version 1: Using 'if' as a Statement
    var ageCategory : String
    if (age < 18) {
        ageCategory = "Teenager"
    } else {
        ageCategory = "Young Adult"
    }

    // Version 2: Using 'if' expression
    // Note: The 'else' case must be present.
    ageCategory = if (age < 18) {
        "Teenager"
    } else {
        "Young Adult"
    }

    // Version 3 - ** Best 👍 **: Using 'when' expression
    ageCategory = when {
        (age < 18) -> "Teenager"
        else -> "Young Adult"
    }

    // Printing results
    println("> $age years old is a $ageCategory")
}