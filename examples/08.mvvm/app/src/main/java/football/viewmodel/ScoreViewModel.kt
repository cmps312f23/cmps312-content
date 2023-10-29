package football.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import football.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@SuppressLint("LongLogTag")
class ScoreViewModel : ViewModel() {
     private val TAG = "LifeCycle->ScoreViewModel ✔"

    // Private mutable state variables
    private var _team1Score = mutableStateOf(0)
    private var _team2Score = mutableStateOf(0)

    // Public State read-only variables
    val team1Score : State<Int> = _team1Score
    val team2Score : State<Int> = _team2Score

    fun onIncrementTeam1Score() {
        _team1Score.value++
    }

    fun onIncrementTeam2Score() {
        _team2Score.value++
    }

    val redCardsCountFlow = DataRepository.getRedCardsCount()

    /*
     - Trigger the flow and start listening for values.
     - Note that this happens when lifecycle is STARTED
     - and stops collecting when the lifecycle is STOPPED
     - Flow will give you a new value every time a new value is emitted.
     A StateFlow, in addition to what a Flow is, it always holds the last value
     */
    val newsFlow: StateFlow<String> = DataRepository.getNews().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ""
    )

    val timeRemainingFlow: Flow<String> =
        DataRepository.countDownTimer(5)
    /*val timeRemainingFlow: StateFlow<String> =
        DataRepository.countDownTimer(5)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = ""
            )*/

    init {
        Log.d(TAG, "Created")
    }

    @SuppressLint("LongLogTag")
    override fun onCleared() {
        Log.d(TAG, "☠️☠onCleared ☠☠")
        super.onCleared()
    }
}