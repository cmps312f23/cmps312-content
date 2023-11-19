package shopapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import shopapp.entity.User
import shopapp.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    var currentUser by mutableStateOf<User?>(null)
    var errorMessage by mutableStateOf("")

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        this.errorMessage = exception.message ?: "Request failed"
        println(">> Debug: Exception thrown: $exception.")
    }

    fun signUp(user: User) = viewModelScope.launch(exceptionHandler) {
        currentUser = authRepository.signUp(user)
    }

    fun signIn(email: String, password: String) = viewModelScope.launch(exceptionHandler) {
        errorMessage =  ""
        currentUser = null
        currentUser = authRepository.signIn(email, password)
    }

    fun setCurrentUser() = viewModelScope.launch(exceptionHandler) {
        val uid = Firebase.auth.currentUser?.uid
        if (uid == null)
            currentUser = null
        else {
            // Get further user details from Firestore
            var user = authRepository.getUser(uid)
            // If no further user details available from Firestore use
            // the basic user details from Firebase.auth.currentUser
            if (user == null) {
                val displayName = Firebase.auth.currentUser?.displayName ?: ""
                val email = Firebase.auth.currentUser?.email ?: ""
                user = User(uid, displayName, email)
            }
            currentUser = user
        }
    }

    fun signOut() {
        Firebase.auth.signOut()
        this.currentUser = null
    }
}