package oop

import java.time.LocalDate

class Faculty(
    firstName: String,
    lastName: String,
    dob: LocalDate,
    val office: String
) : Person(firstName, lastName, dob) {
    override fun toString() = "${super.toString()}. Office: $office"
}