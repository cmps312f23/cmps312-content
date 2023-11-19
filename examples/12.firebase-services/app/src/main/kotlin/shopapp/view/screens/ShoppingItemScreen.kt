package shopapp.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import shopapp.entity.ShoppingItem
import shopapp.view.components.DropdownForMap
import shopapp.view.components.TopBar
import shopapp.viewmodel.ShoppingViewModel

enum class FormMode { ADD, EDIT }

@Composable
//fun ShoppingItemScreen(shoppingItemId: Long? = null, onNavigateBack: () -> Unit) {
fun ShoppingItemScreen(onNavigateBack: () -> Unit) {
    var formMode = FormMode.ADD
    var screenTitle = "Add Shopping Item"
    var confirmButtonLabel = "Add"

    val shoppingViewModel = viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    //val shoppingItemsCount = viewModel.shoppingItemsCount.observeAsState()

    var categoryId by remember { mutableStateOf(shoppingViewModel.selectedShoppingItem?.categoryId ?: "") }
    var productId by remember { mutableStateOf(shoppingViewModel.selectedShoppingItem?.productId ?: "") }
    var quantity by remember { mutableStateOf( shoppingViewModel.selectedShoppingItem?.quantity ?:0) }
    var productName = shoppingViewModel.selectedShoppingItem?.productName ?: ""

    // In case of Edit Mode get the Shopping Item to edit
    if (shoppingViewModel.selectedShoppingItem != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Shopping Item"
        confirmButtonLabel = "Update"
    }

    val categories = shoppingViewModel.categories.collectAsStateWithLifecycle(initialValue = emptyList())
    // Every time categories change ->
    //      Convert a list to a map needed to fill the categories dropdown
    val categoryOptions by remember {
        derivedStateOf {
            categories.value?.associate {
                Pair(it.id, it.name)
            }
        }
    }

    // Every time categoryId change get the products of the selected category
    var products = shoppingViewModel.getProducts(categoryId).collectAsStateWithLifecycle(initialValue = emptyList())
        // Every time products change ->
    //      Convert a list to a map needed to fill the products dropdown
    val productOptions by remember {
        derivedStateOf {
            products.value?.associate {
                if (it != null) {
                    Pair(it.id, "${it.name} ${it.icon}")
                }
                else Pair ("", "")
            }
        }
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    //val scaffoldState = rememberScaffoldState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = { TopBar( title = screenTitle, onNavigateBack) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it)
        ) {
            DropdownForMap(
                label = "Select a Category",
                options = categoryOptions,
                selectedOptionId = categoryId,
                onSelectionChange = { selectedCategoryId, _ ->
                        categoryId = selectedCategoryId
                })

            DropdownForMap(
                label = "Select a Product",
                options = productOptions,
                selectedOptionId = productId,
                onSelectionChange = { selectedProductId, selectedProductName ->
                    productId = selectedProductId
                    productName = selectedProductName
                })

            OutlinedTextField(
                value = if (quantity > 0) quantity.toString() else "",
                onValueChange = {
                    quantity = if (it.isNotEmpty()) it.toInt() else 0
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    if (formMode == FormMode.ADD) {
                        val item = ShoppingItem(
                            productId = productId,
                            productName = productName,
                            quantity = quantity,
                            categoryId = categoryId
                        )
                        shoppingViewModel.addItem(item)
                        // Reset the productId and quantity to enter them again
                        productId = ""
                        quantity = 0
                    } else {
                        shoppingViewModel.selectedShoppingItem?.let {
                            it.productId = productId
                            it.productName = productName
                            it.quantity = quantity
                            it.categoryId = categoryId
                            shoppingViewModel.updateItem(it)
                            onNavigateBack()
                        }
                    }

                    scope.launch {
                        val confirmationMsg = "$productName " + (if (formMode == FormMode.ADD) "added" else "updated")
                        snackbarHostState.showSnackbar( message = confirmationMsg )
                    }
                }) {
                Text(text = confirmButtonLabel)
            }

            //Text(text = "You have ${shoppingItemsCount.value} in your Shopping Card")
        }
    }
}