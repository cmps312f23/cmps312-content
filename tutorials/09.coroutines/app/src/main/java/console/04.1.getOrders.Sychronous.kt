package coroutines.console.synchronous

import coroutines.console.Order
import coroutines.console.User

/*
Consider the getUserOrders function below that needs to make two network calls,
one to login a user and another to fetch list of user’s order
*/
// Synchronous version
fun main() {
    println("Getting user orders (Synchronous - blocking version)...")
    val orders = getUserOrders("sponge", "bob")
    orders.forEach { println(it) }
}

fun getUserOrders(username: String, password: String) : List<Order> {
    val user = login(username, password)
    val orders = fetchOrders(user.userId)
    return orders
}

fun login(username: String, password: String) : User {
    // In real app validate the username and password then return a user object
    return User(123, "Sponge", "Bob")
}

fun fetchOrders(userId: Int) : List<Order> {
    // In real app lookup orders from a database
    val orders = mutableListOf<Order>()
    for (i in 1..5) {
        orders.add( Order(i, i*100) )
    }
    return orders
}