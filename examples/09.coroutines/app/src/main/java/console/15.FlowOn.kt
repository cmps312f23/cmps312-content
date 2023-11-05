package coroutines.console

import kotlinx.coroutines.delay
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

/*
using a flowOn operator: emit values from one thread and collect them in another
*/
val flowNews = flow {
    println("Emitting coroutine running on ${currentCoroutineContext()}")
    emit("Dar")
    delay(100)
    emit("Salam")
    delay(200)
    emit("Done")
}.flowOn(Dispatchers.IO) // The above stream will run in io dispatcher

suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        println("Collecting coroutine running on $coroutineContext")
        flowNews.collect {
            println("Collecting -> $it")
        }
    }

    // Wait for the job to finish otherwise main will exit
    job.join()
}