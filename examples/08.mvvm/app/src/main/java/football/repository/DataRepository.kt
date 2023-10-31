package football.repository

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// https://kotlinlang.org/docs/reference/coroutines/flow.html
@SuppressLint("LongLogTag")
object DataRepository {
    private const val TAG = "LifeCycle->DataRepository"

    fun getNewsList() : List<String> {
        return listOf("news 1", "news 2")
    }

    fun getNews(): Flow<String> =
        flow {
            while (true) {
                delay(3000)
                val news = DummyDb.getNews()
                emit(news)
                Log.d(TAG, news.replace("\n", " "))
            }
        }

    fun countDownTimer(minutes: Int): Flow<String> =
        flow {
            var remainingMilliSeconds = minutes * 60 * 1000L
            val step = 5000L // every 5 seconds
            emit("Remaining: ${convertToMmSs(remainingMilliSeconds)} ")
            while (true) {
                if (remainingMilliSeconds <= 0) break
                remainingMilliSeconds -= step
                delay(step)
                val msg = convertToMmSs(remainingMilliSeconds)
                emit(msg)
                Log.d(TAG, msg)
            }
        }

    private fun convertToMmSs(milliSeconds: Long): String {
        val seconds = milliSeconds / 1000L
        val s = seconds % 60
        val m = seconds / 60 % 60
        return "$m mins $s secs"
    }
}

object DummyDb {
    private val clubs = listOf(
        "Bayern München",
        "Liverpool FC",
        "Paris Saint-Germain",
        "Barcelona",
        "Real Madrid",
        "Manchester City",
        "Atlético Madrid",
        "Inter Milan",
        "Sevilla",
        "Atalanta",
        "AC Milan",
        "Juventus"
    )

    fun getNews(): String {
        val clubs = clubs.shuffled().take(2)
        val scores = (0..4).shuffled().take(2)
        return "${clubs[0]}: ${scores[0]} \n${clubs[1]}: ${scores[1]}"
    }
}