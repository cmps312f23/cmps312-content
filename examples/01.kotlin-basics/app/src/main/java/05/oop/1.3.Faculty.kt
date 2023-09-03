package oop

class Faculty(
    firstName: String,
    lastName: String,
    age: Int,
    val office: String
) : Person(firstName, lastName, age) {
    override fun toString() = "${super.toString()}. Office: ${office}"
}