package shopapp.repository

import android.content.Context
import kotlinx.serialization.json.Json
import shopapp.datasource.ShoppingDB
import shopapp.entity.Category
import shopapp.entity.Product
import shopapp.entity.ProductEntity
import shopapp.entity.ShoppingItemEntity

// Repository, abstracts access to multiple data sources
class ShoppingRepository(private val context: Context) {
    private val shoppingDB by lazy {
        ShoppingDB.getInstance(context)
    }

    private val shoppingItemDao by lazy {
        shoppingDB.shoppingItemDao()
    }

    private val productDao by lazy {
        shoppingDB.productDao()
    }

    fun observeItems() = shoppingItemDao.observeItems()
    suspend fun getItem(itemId: Long) = shoppingItemDao.getItem(itemId)

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: ShoppingItemEntity) : Long {
        val dbItem = shoppingItemDao.getItemByProductId(item.productId)
        return if (dbItem == null) {
            shoppingItemDao.addItem(item)
        } else {
            val quantity = dbItem.quantity + item.quantity
            shoppingItemDao.updateQuantity(dbItem.id, quantity)
            dbItem.id
        }
    }

    suspend fun updateQuantity(id: Long, quantity: Int) = shoppingItemDao.updateQuantity(id,quantity)

    suspend fun updateItem(item: ShoppingItemEntity) {
        shoppingItemDao.updateItem(item)
    }

    suspend fun deleteItem(itemId: Long) = shoppingItemDao.deleteItem(itemId)
    fun observeItemsCount() = shoppingItemDao.observeItemsCount()

    suspend fun getProducts(categoryId: Long) = productDao.getProducts(categoryId)
    fun observeProducts(categoryId: Long) = productDao.observeProducts(categoryId)

    fun observeCategories() = productDao.observeCategories()
    fun observeCategoriesMap() = productDao.observeCategoriesMap()

    suspend fun getCategoriesAndProductCounts() = productDao.getCategoriesAndProductCounts()
    suspend fun getCategoriesAndProducts() = productDao.getCategoriesAndProducts()
    suspend fun getProductCountsPerCategory() = productDao.getProductCountsPerCategory()

    // Used for database initialization
    companion object {
        private fun readData(context: Context, fileName: String) =
                context.assets.open(fileName)
                       .bufferedReader().use { it.readText() }

        suspend fun initDB(shoppingDB: ShoppingDB?, context: Context) {
            if (shoppingDB == null) return

            val productDao = shoppingDB.productDao()
            val categoryCount = productDao.getCategoryCount()
            // If categoryCount = 0 then means the DB is empty
            if (categoryCount == 0) {
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                // Read from json file and write to db
                // 1. Insert categories
                var data = readData( context,"product_categories.json")

                val categories = json.decodeFromString<List<Category>>(data)
                val categoryIds = productDao.addCategories(categories)
                println(">> Debug: categoryIds = productDao.insertCategories(categories) $categoryIds")

                // 2. Insert products
                data = readData( context,"products.json")
                var products = json.decodeFromString<List<Product>>(data)
                println(">> Debug: initDB products $products")

                val productEntities = products.map {
                    // Lookup the category id
                    val category = productDao.getCategory(it.category)
                    ProductEntity(it.name, it.icon, category!!.id)
                }
                val productIds = productDao.insertProducts(productEntities)
                println(">> Debug: productIds = productDao.insertProducts(products) $productIds")
            }
        }
    }
}