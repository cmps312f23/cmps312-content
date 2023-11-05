// More info @ https://kotlinlang.org/docs/flow.html
package demo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

fun intFlow(): Flow<Int> = flow {
    // Producer block
    for (i in 1..9) {
        delay(500)
        emit(i)
        //println(i)
    }
}

suspend fun zipExample() {
    val nums = (1..3).asFlow() // numbers 1..3
    val strs = flowOf("one", "two", "three") // strings
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
        .collect { println(it) } // collect and print
}


suspend fun combineExample() {
    val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
    val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
    val startTime = System.currentTimeMillis() // remember the start time
    nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}

fun main() = runBlocking {
    intFlow() //.collect { println(it) }

    intFlow().filter { it % 2 == 0 }.map { it * 1 }.collect { println(it) }
    //intFlow().filter { it % 2 == 0 }.map { Char(it) }.collect { println(it) }

//    intFlow()
//        .filter {
//            println("Filter $it")
//            it % 2 == 0
//        }
//        .map {
//            println("Map $it")
//            "string $it"
//        }.collect {
//            println("Collect $it")
//        }
//
      //zipExample()
       //combineExample()
}