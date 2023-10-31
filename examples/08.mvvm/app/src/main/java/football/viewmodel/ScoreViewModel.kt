package football.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import football.repository.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@SuppressLint("LongLogTag")
class ScoreViewModel : ViewModel() {
     private val TAG = "LifeCycle->ScoreViewModel ✔"

    // Private mutable state variables
    var team1Score by mutableStateOf(0)
        private set

    var team2Score by mutableStateOf(0)
        private set

    // No need for Public State read-only variables
    //val team1Score : State<Int> = _team1Score
    //val team2Score : State<Int> = _team2Score

    fun onIncrementTeam1Score() {
        team1Score++
    }

    fun onIncrementTeam2Score() {
        team2Score++
    }

    /*
     - Trigger the flow and start listening for values.
     - Note that this happens when lifecycle is STARTED
     - and stops collecting when the lifecycle is STOPPED
     - Flow will give you a new value every time a new value is emitted.
     A StateFlow, in addition to what a Flow is, it always holds the last value
     */
    // You can use WhileSubscribed(5000) to keep the upstream flow active for
    // 5 seconds more after the disappearance of the last collector.
    // That avoids restarting the upstream flow after a configuration change
    val newsFlow = DataRepository.getNews()
    /*val newsFlow: StateFlow<String> = DataRepository.getNews().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ""
    )*/

    /*val timeRemainingFlow: Flow<String> =
        DataRepository.countDownTimer(5) */
    val timeRemainingFlow: StateFlow<String> =
        DataRepository.countDownTimer(5)
            .stateIn(
                scope = viewModelScope,
                // Sharing is started when the first subscriber appears and never stops.
                started = SharingStarted.Lazily,
                initialValue = ""
            )

    init {
        Log.d(TAG, "Created")
        timeRemainingFlow.onEach { Log.d(TAG, it) }
    }

    @SuppressLint("LongLogTag")
    override fun onCleared() {
        Log.d(TAG, "☠️☠onCleared ☠☠")
        super.onCleared()
    }
}