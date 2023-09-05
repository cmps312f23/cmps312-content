package oop

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


class Rectangle2(val height: Int, val width: Int) {
    val isSquare get() = height == width
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    try {
        val person = Person("Ali", "Faleh", LocalDate.parse("1990-01-12"), "1234567")
        println(person)
    } catch (e: Exception) {
        println(e.message)
    }


    val student1 = Student("Fatema", "Saleh", LocalDate.parse("2005-08-12"), 3.4);
    student1.firstName = "Fatima"
    println("> Full name: ${student1.fullName}")
    println("> isUnderAge: ${student1.isUnderAge()}")
    println("> student1.toString(): $student1")

    println("Studies at ${Student.university} in ${Student.city} ${Student.country}. " +
            "Current year: ${Student.getCurrentYear()} ")

    val faculty1 = Faculty("Abbes", "Ibn Firnas", LocalDate.parse("1980-03-20"), "C07-130");
    println("\n> faculty1.toString(): $faculty1")
}