package collections

import java.util.*

fun main() {
    /* Sequence allows lazy evaluation:
    multi-step processing of sequences is executed lazily when possible:
    actual computing happens only when the result of the whole processing
    chain is requested.
     */
    val numbersSequence = sequenceOf("four", "three", "two", "one")
    numbersSequence.map { it.uppercase() }
                   .take(2)
                   .joinToString()
                    //.also execute some processing on the object and returns it
                   .also { println(it) }

    // Sequences represent lazily-evaluated collections
    val numSequence = generateSequence(1, { it + 1 })
    val nums = numSequence.take(10).toList()
    println(nums) // => [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

    // Convert list to a sequence to enable lazy evaluation
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = numbers.asSequence()
            .map { it * 2 } // Lazy
            .filter { it % 2 == 0 } // Lazy
            .reduce(Int::plus) // Terminal (eager)
    println(sum) // 30


    val words = "The quick brown fox jumps over the lazy dog".split(" ")
    //convert the List to a Sequence
    val wordsSequence = words.asSequence()

    val lengthsSequence = wordsSequence.filter { println("filter: $it"); it.length > 3 }
            .map { println("length: ${it.length}"); it.length }
            .take(4)

    println("Lengths of first 4 words longer than 3 chars")
    // terminal operation: obtaining the result as a List
    println(lengthsSequence.toList())
}