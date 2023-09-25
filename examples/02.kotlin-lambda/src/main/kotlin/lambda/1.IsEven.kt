package lambda

fun main() {
    //Lambda assigned to a variable
    val isEven: (Int) -> Boolean = { n -> n % 2 == 0 }
    // Another way of writing the lambda
    //val isEven = { i : Int -> i % 2 == 0 }

    val nums = 1..10
    //Version 1
    var hasEvenNumber = nums.any(isEven)
    //Version 2
    hasEvenNumber = nums.any { n -> n % 2 == 0 }
    //Version 3 - ** Best 👍 **
    hasEvenNumber = nums.any { it % 2 == 0 }

    //Version 1
    var evens = nums.filter(isEven)
    //Version 2
    evens = nums.filter { n -> n % 2 == 0 }
    //Version 3 - ** Best 👍 **
    evens = nums.filter { it % 2 == 0 }

    println("Has an even number: $hasEvenNumber")
    println("Even numbers: $evens")
}