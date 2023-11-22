package shopapp.repository

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import shopapp.entity.Category
import shopapp.entity.Product
import shopapp.entity.ShoppingItem

class ShoppingRepository(private val context: Context) {
    private val shoppingItemCollectionRef by lazy {
        Firebase.firestore.collection("shoppingItems")
    }

    private val categoryCollectionRef by lazy {
        Firebase.firestore.collection("categories")
    }

    fun getShoppingListItems() : Query? {
        val uid = Firebase.auth.currentUser?.uid ?: return null
        return shoppingItemCollectionRef.whereEqualTo("uid", uid) //.get().await()
    }

    suspend fun getItem(itemId: String) : ShoppingItem? {
        val queryResult = shoppingItemCollectionRef.document(itemId).get().await()
        return queryResult.toObject(ShoppingItem::class.java)
    }

    private suspend fun getShoppingItem(uid: String, productId: String) : ShoppingItem? {
        val queryResult = shoppingItemCollectionRef
            .whereEqualTo("uid", uid).whereEqualTo("productId", productId)
            .get().await()
        return queryResult.firstOrNull()?.toObject(ShoppingItem::class.java)
    }

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: ShoppingItem) : String? {
        val uid = Firebase.auth.currentUser?.uid ?: return null
        println(">> Debug: Firebase.auth.currentUser?.displayName -> ${Firebase.auth.currentUser?.displayName}")

        val dbItem = getShoppingItem(uid, item.productId)
        return if (dbItem == null) {
            item.uid = uid
            val queryResult = shoppingItemCollectionRef.add(item).await()
            queryResult.id
        } else {
            val quantity = dbItem.quantity + item.quantity
            updateQuantity(dbItem.id, quantity)
            dbItem.id
        }
    }

    suspend fun updateItem(item: ShoppingItem) {
        shoppingItemCollectionRef.document(item.id).set(item).await()
    }

    suspend fun updateQuantity(itemId: String, quantity: Int) {
        shoppingItemCollectionRef.document(itemId).update("quantity", quantity).await()
    }
    
    suspend fun deleteItem(item: ShoppingItem) {
        shoppingItemCollectionRef.document(item.id).delete().await()
    }

    fun getCategories() : Flow<List<Category>> = flow {
        val queryResult = categoryCollectionRef.orderBy("name", Query.Direction.DESCENDING).get().await()
        val categories = queryResult.toObjects(Category::class.java)
        emit(categories)
    }

    private suspend fun getCategory(categoryName: String) : Category? {
        val queryResult = categoryCollectionRef.whereEqualTo("name", categoryName).get().await()
        return queryResult.firstOrNull()?.toObject(Category::class.java)
    }

    private suspend fun isThereCategoryCollection() : Boolean {
        val queryResult = categoryCollectionRef.limit(1).get().await()
        return queryResult.isEmpty
    }

    private suspend fun addCategory(category: Category) : String {
        val queryResult = categoryCollectionRef.add(category).await()
        return queryResult.id
    }

    suspend fun getProducts(categoryId: String) : List<Product?> {
        if (categoryId.isEmpty()) return emptyList()
        println("categoryId -> $categoryId")
        val queryResult = categoryCollectionRef.document(categoryId)
                            .collection("products")
                            .orderBy("name").get().await()
        return queryResult.toObjects(Product::class.java)
    }

    suspend fun getProduct(categoryId: String, productName: String) : Product? {
        val queryResult = categoryCollectionRef.document(categoryId)
                                               .collection("products")
                                               .whereEqualTo("name", productName)
                                               .get().await()
        return queryResult.firstOrNull()?.toObject(Product::class.java)
    }

    private suspend fun addProduct(categoryId: String, product: Product) : String {
        val queryResult = categoryCollectionRef.document(categoryId).collection("products").add(product).await()
        return queryResult.id
    }

    suspend fun initDB() : String {
        if (!isThereCategoryCollection()) {
            return "Firebase database is already initialized"
        }

        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        // Read from json file and write to db
        // 1. Insert categories
        var data = context.assets.open("product_categories.json")
            .bufferedReader().use { it.readText() }

        val categories = json.decodeFromString<List<Category>>(data)

        // 2. Insert products
        data = context.assets.open("products.json")
            .bufferedReader().use { it.readText() }
        var products = json.decodeFromString<List<Product>>(data)
        println(">> Debug: initDB products $products")

        for (category in categories) {
            val categoryId = addCategory(category)
            println(">> Debug: addCategory(${category.name}) -> $categoryId")

            val categoryProducts = products.filter { it.category == category.name }

            categoryProducts.forEach {
                val product = it.copy(categoryId = categoryId)
                val productId = addProduct(categoryId, product)
                println(">> Debug: addProduct(${product.name}) -> $productId")
            }
        }

        return "Firebase database initialized with ${categories.size} categories & ${products.size} products."
    }
}