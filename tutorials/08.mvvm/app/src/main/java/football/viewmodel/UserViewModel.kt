package football.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var currentUser by mutableStateOf("")
        private set

    val users = mutableStateListOf<String>()

    fun addUser(username: String) {
        users.add(username)
    }

    fun onAuthChange(username: String) {
        currentUser = username
    }
}