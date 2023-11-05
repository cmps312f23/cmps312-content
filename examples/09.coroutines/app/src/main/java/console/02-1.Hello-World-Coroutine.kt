package coroutines.console

import kotlinx.coroutines.*

fun main() {
    val job = CoroutineScope(CoroutineName("hello")).launch {
        delay(1000L)
        println("World ! ")
    }
    println("Hello")
}