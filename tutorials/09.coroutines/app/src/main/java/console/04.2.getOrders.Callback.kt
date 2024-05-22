package coroutines.console.callback

import coroutines.console.Order
import coroutines.console.User
import java.lang.Thread.sleep
import kotlin.concurrent.thread

/*
Aside the problem with callback hell which is inherent with this approach especially when we have
more functions to compose, it is also difficult to cancel background operations which consequently
leads to memory leaks when the lifecycle owner gets destroyed.
*/

// Callback version
fun main() {
    println("Getting user orders (Callback version)...")
    getUserOrders("sponge", "bob") { orders ->
        orders.forEach { println(it) }
    }
}

fun getUserOrders(username: String, password: String, callback: (List<Order>) -> Unit) {
    login(username, password) { user ->
        fetchOrders(user.userId) { orders ->
            // When the result is ready, pass it to main() using the callback
            callback(orders)
        }
    }
}

fun login(username: String, password: String, callback: (User) -> Unit) {
    // Run in a background thread
    // In real app validate the username and password then return a user object
    thread {
        sleep(2000)
        val user = User(123, "Sponge", "Bob")
        callback(user)
    }
}

fun fetchOrders(userId: Int, callback: (List<Order>) -> Unit) {
    // Run in a background thread
    // In real app lookup orders from a database
    thread {
        sleep(5000)
        val orders = mutableListOf<Order>()
        for (i in 1..5) {
            orders.add(Order(i, i * 100))
        }
        callback(orders)
    }
}