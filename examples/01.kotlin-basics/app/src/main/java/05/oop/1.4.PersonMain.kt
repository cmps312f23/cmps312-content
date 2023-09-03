package oop

fun main() {
    val person = Person("Ali", "Faleh", 25, "1234567")
    println(person)

    val student1 = Student("Fatima", "Saleh", 23, 3.4);
    println("> Full name: ${student1.fullName}")
    println("> isUnderAge: ${student1.isUnderAge()}")
    println("> student1.toString(): $student1")

    println("Studies at ${Student.university} in ${Student.city} ${Student.getCountry()}")

    val faculty1 = Faculty("Abbes", "Ibn Firnas", 45, "C07-130");
    println("\n> faculty1.toString(): $faculty1")
}