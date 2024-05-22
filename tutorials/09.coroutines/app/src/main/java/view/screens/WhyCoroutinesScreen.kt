package coroutines.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coroutines.view.components.ClickCounter
import coroutines.view.components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.util.Random
import kotlin.concurrent.thread

private val TAG = "WhyCoroutines"

@Composable
fun WhyCoroutinesScreen() {
    val coroutineScope = rememberCoroutineScope()
    var result by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBar("Why Coroutines") }
    ) {
        Column(
            modifier = Modifier.padding(it).verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                ClickCounter(modifier = Modifier.weight(1F))
                Text(
                    text = "Click to check that the UI is still responsive \uD83D\uDE03",
                    modifier = Modifier.weight(1F)
                )
            }

            Text(text = result)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        result = "Started long running task on Main Thread"
                        Log.i(TAG, "Running on ${Thread.currentThread()} thread.")
                        result = nextProbablePrime().toString()
                    }
                ) {
                    Text(text = "Long Running Task on Main Thread")
                }
                Text(
                    text = "\uD83D\uDC4E\uD83D\uDC4E\uD83D\uDC4E Long Running task that will block the main thread and freeze the App",
                    modifier = Modifier.weight(1F)
                )
            }

            // Callback version
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        result = "Started long running task on Background Thread"
                        getPrimeBigInt { primeInt ->
                            Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
                            result = primeInt.toString()
                        }
                        Log.i(TAG, "Running on ${Thread.currentThread()} thread.")
                    }
                ) {
                    Text(text = "Long Running Task On Background Thread")
                }
                Text(
                    text = "\uD83D\uDC4E But thread is costly. UI can only be accessed from the Main thread. Callback required to update the UI.",
                    modifier = Modifier.weight(1F)
                )
            }

            // Coroutine version
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        result = "Started long running task using Coroutine"
                        coroutineScope.launch {
                            Log.i(
                                TAG,
                                "coroutineScope Running on ${Thread.currentThread().name} thread."
                            )
                            val primeInt = getPrimeBigInt()
                            result = primeInt.toString()
                        }
                    }
                ) {
                    Text(text = "Long Running Task using Coroutine")
                }
                Text(
                    text = "\uD83D\uDC4D Coroutine lightweight, easier alternative to use without callbacks.",
                    modifier = Modifier.weight(1F)
                )
            }

            Text(text = "- Coroutines are like light-weight threads. They\n" +
                    "are more efficient and yield better performance\n" +
                    "- Multiple coroutines can run within a thread\n" +
                    "- Easier cancellation of a long running coroutine\n" +
                    "- Easier exception handling\n" +
                    "- Easier to run coroutines in parallel to improve the app performance\n" +
                    "- Easier to switch the coroutine execution between threads\n")
        }
    }
}

// Example realistic long running computation
// Calculate the next probable prime based on a randomly generated BigInteger
// which happens to be a fairly expensive task (since this calculation is based
// on a random it will not run in deterministic time)
private fun nextProbablePrime(): BigInteger {
    //Just pretend to work hard for 5 seconds
    Thread.sleep(5000)
    return BigInteger(1500, Random()).nextProbablePrime()
}

// Coroutine version
private suspend fun getPrimeBigInt() =
    withContext(Dispatchers.Default) {
        Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
        nextProbablePrime()
    }

// Callback version
private fun getPrimeBigInt(callBack: (BigInteger) -> Unit) {
    thread {
        Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
        val primeInt = nextProbablePrime()

        callBack(primeInt)
        // But trying to access the UI from this backgroud thread will cause an error
        //result = primeInt.toString()
    }
}