package coroutines.console.coroutine

import coroutines.console.Order
import coroutines.console.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
Coroutines allow us to write asynchronous code
in a sequential manner while still ensuring that
long running operations run in the background
and not block on the UI thread.
*/

// Coroutine implementation
suspend fun main() {
    val job = CoroutineScope(Dispatchers.IO).launch {
        println("Getting user orders (Coroutine version)...")
        val orders = getUserOrders("sponge", "bob")
        orders.forEach { println(it) }
    }
    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}

 suspend fun getUserOrders(username: String, password: String) = withContext(Dispatchers.IO) {
    val user = login(username, password)
    fetchOrders(user.userId)
}

suspend fun login(username: String, password: String) : User {
    // In real app validate the username and password then return a user object
    delay(2000)
    return User(123, "Sponge", "Bob")
}

suspend fun fetchOrders(userId: Int) : List<Order> {
    // In real app lookup orders from a database
    delay(3000)
    val orders = mutableListOf<Order>()
    for (i in 1..5) {
        orders.add( Order(i, i*100) )
    }
    return orders
}