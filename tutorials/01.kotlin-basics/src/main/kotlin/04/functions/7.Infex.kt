package functions

/*
Infix function must satisfy the following requirements:
- Must be member function or extension function.
- Must have a single parameter.
- The parameter must not accept a variable number of arguments
  and must have no default value.
 */
infix fun Int.add(b : Int) : Int = this + b

fun main() {
    val x = 10.add(20)
    val y = 10 add 20        // infix call

    println("x: $x & y: $y")
}
