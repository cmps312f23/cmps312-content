package lambda.zip

data class Person(
    val name: String,
    val age: Int
)

val names = listOf("Ibrahim", "Ali", "Ahmed", "Samira", "Kawtar", "Khadija")
val ages = listOf(21, 15, 25, 25, 42, 21)

fun people(): List<Person> =
    names.zip(ages) { name, age ->
        Person(name, age)
    }

fun main() {
    println( names.zip(ages) )
    people().sortedBy { it.name }.forEach(::println)
}