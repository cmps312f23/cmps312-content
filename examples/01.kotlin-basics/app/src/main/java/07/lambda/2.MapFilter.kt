package lambda

fun main() {
    val double = { e: Int -> e * 2 }
    println("Double of 2 = ${double(2)}")

    println("\nEven numbers doubled:")
    val nums = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    nums.filter { it % 2 == 0}
         .map { e -> e * 2 }
         .forEach { println(it) }

    // Imperative
    var numsSum = 0
    for(n in nums)
        numsSum += n

    //Declarative
    var total = nums.reduce { sum, n -> sum + n }
    //Another way with the ability to set the initial value of sum
    total = nums.fold(0) { sum, n -> sum + n }
    val product = nums.fold(1) { runningProduct, item -> runningProduct * item }

    //Short form
    val sum = nums.sum()
    val count = nums.count()
    val average = nums.average()
    val max = nums.maxOrNull()
    val min = nums.minOrNull()
    println("\nSum: $sum, Count: $count, Average: $average, Max: $max, Min: $min, Product: $product")

    // mapNotNull
    val strings = listOf("1", "3", "5", "a", "c", "d")
    val ints = strings.mapNotNull { it.toIntOrNull() }
    println(ints)


}