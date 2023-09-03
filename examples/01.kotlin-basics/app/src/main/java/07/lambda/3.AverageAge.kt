package lambda

data class Employee(
        val name: String, val city: String, val age: Int
)

fun main() {
    val employees = listOf(
        Employee("Sara Faleh", "Doha", 30),
        Employee("Mariam Saleh", "Istanbul", 22),
        Employee("Ali Maleh", "Doha", 24)
    )

    val avgAge = employees.filter { it.city == "Doha" }
        .map {
            println("processing $it")
            it.age
        }
        .average()

    println("Average age: $avgAge")
}