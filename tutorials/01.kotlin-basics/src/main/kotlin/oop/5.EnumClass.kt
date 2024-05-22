package oop

/*
    - Each enum constant is an object and each object is separated by a comma
 */

enum class Gender {
    FEMALE, MALE
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

/*
    - Enum classes can be initialized
    - If you are interested in accessing the parameter (in this case 'capital'), you must declare it as a 'val' or 'var'
 */
enum class Country(val capital: String) {
    PANAMA("Panama"),
    USA("Washington, D.C"),
    SWEDEN("Stockholm");
}

fun main() {
    // ----------------------------
    // Useful methods
    // ----------------------------
    println(Country.USA.capital) // --> Stockholm
    println(Country.SWEDEN.name) // --> SWEDEN
    println(Country.SWEDEN.ordinal) // --> 2
    println(Country.SWEDEN.toString()) // --> SWEDEN

}