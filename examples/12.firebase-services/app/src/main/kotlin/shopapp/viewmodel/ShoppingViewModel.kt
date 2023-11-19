package shopapp.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import shopapp.entity.ShoppingItem
import shopapp.repository.ShoppingRepository

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    //private val shoppingRepository = ShoppingRepository(appContext)
    private val shoppingRepository = ShoppingRepository(appContext)

    var selectedShoppingItem : ShoppingItem? = null
    private var shoppingListUpdateListener : ListenerRegistration? = null

    //val shoppingList = shoppingRepository.getItems()
    val shoppingList = mutableStateListOf<ShoppingItem>()

    init {
        getShoppingListItems()
    }

    // Observe Firestore ShoppingList collection and get notified of changes
    private fun getShoppingListItems() {
        shoppingListUpdateListener?.remove()

        val query = shoppingRepository.getShoppingListItems()
        if (query == null) {
             shoppingList.clear()
             return
        }
        shoppingListUpdateListener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                println(">> Debug: Shopping List Update Listener failed. ${e.message}")
                return@addSnapshotListener
            }
            val results = snapshot?.toObjects(ShoppingItem::class.java)
            shoppingList.clear()
            results?.let {
                shoppingList.addAll( it )
            }
        }
    }

    // ToDo
    //val shoppingItemsCount = shoppingRepository.getCount()

    fun addItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }

    fun updateItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateItem(item)
    }

    fun updateQuantity(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateQuantity(item.id, item.quantity)
    }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(item)
    }

    val categories = shoppingRepository.getCategories()

    fun getProducts(categoryId: String) = flow {
        emit ( shoppingRepository.getProducts(categoryId) )
    }

    fun initDB() = flow {
        val result = shoppingRepository.initDB()
        emit(result)
    }

    override fun onCleared() {
        shoppingListUpdateListener?.remove()
        super.onCleared()
    }
}