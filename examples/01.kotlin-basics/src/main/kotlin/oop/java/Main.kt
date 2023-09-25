import oop.kt.Person
import java.time.LocalDate

fun main() {
    val person1 = Person("Sara", LocalDate.parse("2005-08-12"))
    //person1.setDob(LocalDate.parse("2004-08-12"))
    //println(person1.getName() + ", " + person1.getAge() + " years old")
    person1.dob = LocalDate.parse("2006-08-12")

    try {
        person1.gpa = 5
    } catch (e: Exception) {
        println(e.message)
    }
    //person1.age = 30
    println("${person1.name},  ${person1.age} years old. GPA ${person1.gpa}")
}
