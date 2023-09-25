data class Grocery(
    val name: String, val category: String,
    val unit: String, val unitPrice: Double,
    val quantity: Int
)

fun main() {
    val groceries = listOf(
        Grocery("Tomatoes", "Vegetable", "Kg", 3.0, 3),
        Grocery("Mushrooms", "Vegetable", "Kg", 4.0, 1),
        Grocery("Bagels", "Bakery", "Pack", 1.5, 2),
        Grocery("Olive oil", "Pantry", "Bottle", 6.0, 1),
        Grocery("Ice cream", "Frozen", "Pack", 3.0, 2)
    )

    groceries.groupBy { it.category }.forEach {
        val groceriesStr = it.value.joinToString { it.name }
        println("${it.key}: $groceriesStr")
    }

    var itemsStr = groceries.fold("") { string, item -> "$string  ${item.name}," }
    // Version 2 - ** Best 👍 **
    itemsStr = groceries.joinToString { it.name }
    println("Grocery items: $itemsStr")

    val payment = 50
    val change = groceries.fold(50.0)
                { change, item -> change - item.unitPrice * item.quantity }
    println("Change from $payment: $change")

    val categories = groceries.map { it.category }.distinct().sorted()
    println("Grocery categories: $categories")
}