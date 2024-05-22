package shopapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import shopapp.entity.ShoppingItem
import shopapp.entity.ShoppingItemEntity
import shopapp.repository.ShoppingRepository

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)

    var selectedShoppingItem : ShoppingItem? = null

    val shoppingListFlow = shoppingRepository.observeItems()
    val shoppingItemsCountFlow = shoppingRepository.observeItemsCount()

    fun addItem(item: ShoppingItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }

    fun updateItem(item: ShoppingItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateItem(item)
    }

    fun updateQuantity(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateQuantity(item.id, item.quantity)
    }

    fun deleteItem(itemId: Long) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(itemId)
    }

    val categoriesFlow = shoppingRepository.observeCategories()
    val categoriesMapFlow = shoppingRepository.observeCategoriesMap()

    fun getProducts(categoryId: Long) = flow {
        emit ( shoppingRepository.getProducts(categoryId) )
    }

    fun observeProducts(categoryId: Long) = shoppingRepository.observeProducts(categoryId)

    suspend fun getCategoriesAndProductCounts() = shoppingRepository.getCategoriesAndProductCounts()
    suspend fun getCategoriesAndProducts() = shoppingRepository.getCategoriesAndProducts()
    suspend fun getProductCountsPerCategory() = shoppingRepository.getProductCountsPerCategory()
}