package coroutines.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class FibonacciViewModel : ViewModel() {
    var nextFibonacciValue by mutableStateOf(0L)
    var isJobRunning by mutableStateOf(false)

    var job: Job? = null

    fun startFibonacci() {
        cancelFibonacci()
        job = viewModelScope.launch {
            isJobRunning = true
            fibonacci()
        }
    }

    fun cancelFibonacci() {
        job?.let {
            if (it.isActive) {
                it.cancel()
                isJobRunning = false
            }
        }
    }

    /*
     The Fibonacci series is a series where the next term is the sum of pervious two terms.
     Fn = Fn-1 + Fn-2
     The first two terms of the Fibonacci sequence is 0 followed by 1.
    The Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
    */
    suspend fun fibonacci() = withContext(Dispatchers.Default) {
        try {
            // 0, 1, 2, 3, 5, 8
            var terms = Pair(0L, 1L)
            // this sequence is infinite
            while (true) {
                nextFibonacciValue = terms.first
                terms = Pair(terms.second, terms.first + terms.second)

                ensureActive()  // check - if job cancelled exit the loop
                //yield()  // check - if job cancelled exit the loop
                //if (!isActive) return@withContext  // check - if job cancelled exit the loop
                //println("${terms.first}")

                // Suspend the function for 600ms
                delay(600)
            }
            println("Job done!")
        } catch (e: CancellationException) {
            println("Job cancelled!")
        }
    }

    // When add is called the main thread will block
    // This is a Blocking call = Synchronous call
    // val sum = add(20, 30)
    fun add(x: Int, y: Int) = x + y
}