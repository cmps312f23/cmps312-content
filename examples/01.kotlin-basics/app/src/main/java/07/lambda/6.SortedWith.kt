package lambda

fun main() {
    val employees = listOf<Employee>(
        Employee("Sara Faleh", "Doha",30),
        Employee("Mariam Saleh", "Istanbul",30),
        Employee("Ali Maleh", "Doha",24)
    )

    // Sort by age then by name
    val sortedEmpolyees = employees.sortedWith(
          compareBy(Employee::age, Employee::name)
    )

    sortedEmpolyees.forEachIndexed{ i, employee -> println("${i+1}: " + employee) }
}
