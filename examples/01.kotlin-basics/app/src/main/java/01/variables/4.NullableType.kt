package variables

fun main() {
    //To make a variable nullable simply add ? to the data type
    var name : String? = "Ahmed"
    name = null
    name = "Ali"

    val length = if (name != null) name.length else 0
    // Better syntax is to use the Elvis operator (?:)
    val len = name?.length ?: 0

    println("The length of \"$name\" is $length")

    // ** Best 👍 ** : Checking for null using the 'let' operator
    // The 'let' block only executes if 'name' is not null
    name?.let {
        // 'it' is the non-null value stored in 'name'
        println("$it is not null")
    }
}