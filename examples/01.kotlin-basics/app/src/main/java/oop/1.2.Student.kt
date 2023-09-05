package oop

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

/*
    - To inherit from a class, write the name of the base class after a colon.
    - There are two things to keep in mind when inheriting:
        1. If the child class has a primary constructor, then the parent class
            must be initialized using the parameters of the primary constructor.
        2. If the child class has no primary constructor, then each secondary
            constructor has to initialize the base type using the 'super' keyword.
 */

class Address(val street: String = "", val city: String = "")

@RequiresApi(Build.VERSION_CODES.O)
class Student(firstName: String,
              lastName: String,
              dob: LocalDate,
              val gpa: Double
) : Person(firstName, lastName, dob) {

    var address = Address()

    /*
    - Override a base class method
    - super keyword to call the implementation of the base class
   */
    override fun toString() = "${super.toString()}. GPA: ${gpa}"

    /* Kotlin classes do not have "static" properties or methods.
       So we must use a "companion object" instead.
       The companion objects allows the declaration of methods and properties that
       belong to the class. They can be accessed by using only the class name
       without create an instance of the class
     */
    companion object {
        val university = "Qatar University"
        val city = "Doha"
        val country = "Qatar"
        fun getCurrentYear() = LocalDate.now().year
    }
}