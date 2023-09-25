package oop.kt;

import java.security.InvalidParameterException
import java.time.LocalDate;
import java.time.Period;

class Person(val name: String, var dob: LocalDate) {
    /*init {
        if (gpa !in 1..4) {
            gpa = 0
            throw InvalidParameterException("GPA invalid")
        }

    } */
    var gpa: Int = 0
        set(value) = if (value in 1..4) field = value else throw InvalidParameterException("$value is invalid gpa")

    val age get() = Period.between(dob, LocalDate.now()).years

    /*
    private LocalDate dob;

    public Person(String name, LocalDate dob) {
        this.name = name;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) { this.dob = dob; }
*/
}