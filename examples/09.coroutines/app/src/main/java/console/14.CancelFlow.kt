package coroutines.console

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

//  Like coroutines, a flow can be canceled in a suspending function
suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        numsFlow.collect {
            println("Got item -> $it")
        }
    }
    delay(500)
    job.cancel()
    println("Done")
}

val numsFlow = flow {
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}