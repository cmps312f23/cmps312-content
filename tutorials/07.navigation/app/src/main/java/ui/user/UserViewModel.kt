package ui.user

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class User(var userId: Int, val firstName: String,
                val lastName: String, val email: String) {
    val name : String
        get() = "$firstName $lastName"
}

class UserViewModel : ViewModel() {
    val users = mutableStateListOf(
        User(1, "Ahmed", "Faleh", "ahmed@test.com"),
        User(2, "Fatima", "Faleh", "fatima@test.com"),
        User(3, "Ali", "Saleh", "ali@test.com"),
        User(4, "Samir", "Saghir", "samir@test.com"),
        User(5, "Samira", "Saghir", "samira@test.com"),
        User(6, "Abbas", "Ibn Firnas", "abbas@test.com"),
    )

    val usersCount : Int
        get() = users.size

    fun getUser(userId: Int) = users.find { it.userId == userId }

    fun addUser(user: User) {
        val userId = users.maxOfOrNull { it.userId } ?: 1
        user.userId = userId + 1
        users.add(user)
    }
}