package qu.lingosnacks.repository

import android.content.Context
import qu.lingosnacks.entity.User
import kotlinx.serialization.json.Json

object UserRepository {
    var users = mutableListOf<User>()
    var currentUser: User? = null

    fun initUsers(context: Context): List<User> {
        if (users.isEmpty()) {
            val userJsonString =
                context.assets.open("users.json").bufferedReader().use { it.readText() }
            users = Json { ignoreUnknownKeys = true }.decodeFromString(userJsonString)
            users = users.filter { it.role == "Teacher" }.toMutableList()
        }
        return users
    }

    fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    fun login(email: String, password: String): User? {
        currentUser = users.firstOrNull { it.email == email && it.password == password }
//        println("Email: $email\nPassword: $password")
//        println("Reg currentUser: $currentUser")
        return currentUser
    }

    fun loginBool(email: String, password: String): Boolean {
        login(email, password)
//        println("Bool currentUser: $currentUser")
        return currentUser != null
    }

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (users.any { it.email == email }) return false
        if (password != confirmPassword) return false
        if (users.add(User(firstName, lastName, email, password, "Teacher"))) {
            currentUser = users.last()
            return true
        }
        return false
    }

    fun logout() {
        println("Before logout: $currentUser")
        currentUser = null
        println("After logout: $currentUser")
    }
}