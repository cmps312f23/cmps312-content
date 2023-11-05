package coroutines.console

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.math.BigInteger
import java.util.*

// flow .... emit .... collect
suspend fun main() {
    println("Receiving primes")
    primesFlow().collect { // like observer
        println("Receiving $it")
    }
    println("Receiving end")

    symbolsFlow().collect { // like observer
        println("Receiving $it")
    }

    (1..5).asFlow()
        .filter { it % 2 == 0 }
        .map { it * it }
        .collect { println(it.toString()) }

    val result = (1..5).asFlow()
                       .reduce { a, b -> a + b }
    println("result: $result")
}

//like observable
//flow builder
fun primesFlow() = flow {
    val primes = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    primes.forEach {
        delay(it * 100L)
        emit(it)
    }
}

fun symbolsFlow() = flow {
    emit("🌊") // Emits the value upstream ☝
    delay(500)
    emit("⚽")
    delay(300)
    emit("🎉")
}