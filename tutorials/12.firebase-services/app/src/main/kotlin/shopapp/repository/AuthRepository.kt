package shopapp.repository

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import shopapp.entity.User
import java.lang.Exception

class AuthRepository {

    private val userCollectionRef by lazy {
        Firebase.firestore.collection("users")
    }

    suspend fun signUp(user: User) : User? = withContext(Dispatchers.IO) {
        val authResult =
            Firebase.auth.createUserWithEmailAndPassword(user.email, user.password).await()

        authResult?.user?.let {
            val userProfileChangeRequest = userProfileChangeRequest {
                displayName = "${user.firstName} ${user.lastName}"
                photoUri = Uri.parse("http://pngimg.com/uploads/spongebob/spongebob_PNG61.png")
            }
            // Add displayName and photoUri to the user
            // Unfortunately it does not allow adding custom attribute such as role
            it.updateProfile(userProfileChangeRequest).await()

            // You may send the user a link to confirm their email address
            // it.sendEmailVerification().await()

            // If needed, add further user details to Firestore
            user.uid = it.uid
            println(">> Debug: signUp.user.uid : ${user.uid}")
            addUser(user)
            user
        } ?: throw Exception("Signup failed")
    }

    private suspend fun addUser(user: User) {
        userCollectionRef.document(user.uid).set(user).await()
    }

    suspend fun signIn(email: String, password: String) : User = withContext(Dispatchers.IO) {
        val authResult = Firebase.auth.signInWithEmailAndPassword(email, password).await()
        println(">> Debug: signIn.authResult : ${authResult.user?.uid}")

        authResult?.user?.let {
            getUser(it.uid)
        } ?: throw Exception("Email and/or password invalid")
    }

    suspend fun getUser(uid: String) = withContext(Dispatchers.IO) {
        userCollectionRef.document(uid)
                     .get().await().toObject(User::class.java)
    }
}