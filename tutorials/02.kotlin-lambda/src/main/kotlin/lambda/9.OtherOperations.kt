package lambda

fun String.isPhoneNumber() =
    length == 7 && all { it.isDigit() }

fun main() {
    println( "2345678 is phone number: ${"2345678".isPhoneNumber()}" )

    // Demo of any, find, firstOrNull
    val people = listOf(
            Person("Ali", 31, Gender.MALE),
            Person("Sara", 20, Gender.FEMALE),
            Person("Khadija", 31, Gender.FEMALE))

    val result = people.any { it.name.startsWith("A") }
    println("Any one with name starting with 'A': $result")

    var person = people.find { it.age > 30 }
    println("First person older than 30:  $person")
    person = people.firstOrNull { it.age > 30 }
    println("First person older than 30:  $person")

    // Demo of partition
    val (even, odd) = listOf(1, 2, 3, 4).partition { it % 2 == 0 }
    println("Evens: $even")
    println("Odds: $odd")

    // Demo of zip
    val names = listOf("Ali", "Sara", "Fatima")
    val grades = listOf(80.5, 90, 75.5)
    val students = names.zip(grades)
    println(students)

    // Demo of groupBy
    println("> People grouped by age:")
    println(people.groupBy { it.age })
    println("> People grouped by gender:")
    println(people.groupBy { it.gender })
}
