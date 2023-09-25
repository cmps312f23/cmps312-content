package variables

fun main() {
    // Declaring a nullable and non-nullable variable
    val iCannotBeNull = "Not Null"
    val iCanBeNull: String? = null

    // The following produces an error:
    //iCannotBeNull = null

    // Version 1: Checking for null using 'if'
    if (iCanBeNull != null) {
        println("It is not null")
    }

    // Version 2 ** Best 👍 ** - : Checking for null using the 'let' operator
    // The 'let' block only executes if 'iCanBeNull' is not null
    iCanBeNull?.let {
        // 'it' is the non-null value stored in 'iCanBeNull'
        println("$it is not null")
    }

    // Version 3: Calling methods of null and non-null variables
    val measure = iCanBeNull?.length // Only executes if 'iCanBeNull' is not null
    val size = iCannotBeNull.length // It is guaranteed that length will not be null

    println("measure: $measure")
    // Notice the below statement does not crash the program.
    measure.toString()

    // Version 4: Using the elvis operator to still get a value in case 'iCanBeNull' is null
    val distance = iCanBeNull?.length ?: -1
    println("distance: $distance")
}