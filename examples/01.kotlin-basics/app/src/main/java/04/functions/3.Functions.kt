package functions

fun display(value : Any) : Unit {
    println(value)
}

fun greet(msg: String = "Hello", name: String) = "$msg $name"

val greet2 = { msg: String, name: String -> "$msg $name" }  // Lambda

val doubler = { x: Int -> x * 2 }

fun main() {
    println( greet(name = "Ali") )

    display(greet2("Salamou Aleikoum", "Ali"))

    display( doubler(10) )
}