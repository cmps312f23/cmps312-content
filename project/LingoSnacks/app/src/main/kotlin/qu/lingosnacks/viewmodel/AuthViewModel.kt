package qu.lingosnacks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import qu.lingosnacks.entity.User
import qu.lingosnacks.entity.UserInfo
import qu.lingosnacks.repository.AuthRepository

class AuthViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val authRepository = AuthRepository(appContext)

    // ToDo: Initialize _currentUser by calling userRepository.getCurrentUser to get cached authenticated user
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser as StateFlow<User?>

    fun signIn(email: String, password: String): User? {
        val user = authRepository.signIn(email, password)
        if (user != null) {
            _currentUser.value = user
        }
        return user
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun signUp(user: User) {
        authRepository.signUp(user)
        _currentUser.value = user
    }

    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
    }

    fun getCurrentUserInfo(): UserInfo {
        var userInfo = UserInfo()
        currentUser.value?.let {
            userInfo = UserInfo(
                currentUser.value!!.uid,
                currentUser.value!!.email,
                currentUser.value!!.role
            )
        }
        return userInfo
    }
}