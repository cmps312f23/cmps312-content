package variables

fun main() {

    // slash slash line comment

    /*
     slash star
     block comment
    */

    // Initializing Variables by assigning a value
    // Note: No need to specify the datatype since we are setting a value
    /* Type is auto-inferred - The variable datatype is
        derived from the assigned value */
    val name = "John Smith" // String
    // val means final - cannot change once initialized
    val age = 30 // integer
    var married = false // boolean

    // 'val' cannot be reassigned --> The following produce errors:
    // name = "James Smith"
    // age = 31 --> Error

    // 'var' can be reassigned --> No error produced
    married = true

    //Mutable - can be changed
    var x = 5
    x += 1

    // Initializing Variables without a value
    // Note: When no value is assigned, we must specify the datatype
    val lastname: String
    val month: Int

    // Printing results using 'String templates' instead of string concatenation.
    println("> Name: $name")
    println("> Age: $age")
    println("> Married: $married")

    var myVar: Any //Equivalent to Object Java. Avoid unless necessary
    myVar = 10
    myVar = "Ali"

    //Smart auto-cast
    if (myVar is String) {
        println(myVar.last())
    }

}