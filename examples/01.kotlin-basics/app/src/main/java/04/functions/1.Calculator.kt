package math.functions

// Function with block body
fun sum(a: Int, b: Int) : Int {
    return a + b
}

// Function with expression body (Omit return type)
fun sum1(a: Int, b: Int) =  a + b

//Arrow Function - called Lambda expression
val sum2 = { a: Int, b: Int -> a + b }
val sum3: (Int, Int) -> Int = { a, b -> a + b }

// Function with expression body
// Omit return type
fun add(x: Int, y: Int) = x + y

fun sub(x: Int, y: Int) = x - y

fun mult(x: Int, y: Int) = x * y

fun div(x: Int, y: Int) = x / y
