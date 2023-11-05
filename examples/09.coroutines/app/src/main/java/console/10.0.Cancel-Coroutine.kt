package coroutines.console

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        //fibonacci -> flow (series of values)
        fibonacci().collect {
            print("$it, ")
        }
    }

    // Cancel the job after 5 seconds
    delay(5000)
    job.cancel()

    job.invokeOnCompletion {
        if (job.isCancelled)
            println("\n>>> Job cancelled <<<")
        else
            println("\n>>> Job done <<<")
    }
    // Wait for the job to finish otherwise main will exit
    job.join()
}

// 0, 1, 1, 2, 3, 5,
// flow -> sequence values NOT 1 value
// add(10, 20) -> 1 result
fun fibonacci() = flow {
    var terms = Pair(0L, 1L)
    // this sequence is infinite
    while (true) {
        yield()  // check - if job cancelled exit the loop
        emit(terms.first)
        terms = Pair(terms.second, terms.first + terms.second)
        // Suspend the function for 400ms
        delay(1000)
    }
}
