package lambda

data class Person(val name: String, val age: Int, val gender: Gender)
enum class Gender { MALE, FEMALE }

fun main() {
    val people = listOf(
            Person("Ali", 29, Gender.MALE),
            Person("Ahmed", 31, Gender.MALE),
            Person("Samira", 18, Gender.FEMALE)
    )

    //Version 1 - ** Best 👍 **
    people.forEach { println(it) }
    // Version 2 -using a member reference (i.e., referencing println function)
    people.forEach(::println)

    //Version 1 - ** Best 👍 **
    var oldestPerson = people.maxByOrNull { it.age }
    //Version 2 -  using a member reference (i.e., referencing age property of person)
    oldestPerson = people.maxByOrNull(Person::age)

    println("Oldest Person: $oldestPerson")
}