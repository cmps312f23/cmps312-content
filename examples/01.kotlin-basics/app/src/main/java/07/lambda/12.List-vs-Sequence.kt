package `07`.lambda

fun main() {
    println("Output using a list")
    val nums = listOf(1, 2, 3, 4)
    var result = nums.map {
        println(it * 2)
        it * 2
    }
        .find { it == 4 }

    println("$result found")


    println("\nOutput using a sequence")
    val numsSequence = sequenceOf(1, 2, 3, 4)
    result= numsSequence.map {
        println(it * 2)
        it * 2
    }
        .find { it == 4 }
    println("$result found")

}