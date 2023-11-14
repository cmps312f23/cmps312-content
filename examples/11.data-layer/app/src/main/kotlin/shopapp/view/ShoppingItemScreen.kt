package shopapp.view

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import shopapp.entity.ShoppingItemEntity
import shopapp.view.components.Datepicker
import shopapp.view.components.DialogBox
import shopapp.view.components.Dropdown
import shopapp.view.components.TopBar
import shopapp.viewmodel.ShoppingViewModel

enum class FormMode { ADD, EDIT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun ShoppingItemScreen(shoppingItemId: Long? = null, onNavigateBack: () -> Unit) {
fun ShoppingItemScreen(onNavigateBack: () -> Unit) {
    var openDialogBox by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    val context = LocalContext.current
    var formMode = FormMode.ADD
    var screenTitle = "Add Shopping Item"
    var confirmButtonLabel = "Add"

    val viewModel = viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val shoppingItemsCount = viewModel.shoppingItemsCountFlow.collectAsStateWithLifecycle(initialValue = 0)

    var categoryId by remember { mutableStateOf(viewModel.selectedShoppingItem?.categoryId ?: 0) }
    var productId by remember { mutableStateOf(viewModel.selectedShoppingItem?.productId ?: 0) }
    var productName by remember { mutableStateOf(viewModel.selectedShoppingItem?.productName ?: "") }
    var quantity by remember { mutableStateOf( viewModel.selectedShoppingItem?.quantity ?:0) }

    val coroutineScope = rememberCoroutineScope()
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var updatedDate by remember { mutableStateOf( viewModel.selectedShoppingItem?.updatedDate ?: today) }

    // In case of Edit Mode get the Shopping Item to edit
    if (viewModel.selectedShoppingItem != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Shopping Item"
        confirmButtonLabel = "Update"
    }

    val categories = viewModel.categoriesMapFlow.collectAsStateWithLifecycle(initialValue = mapOf(0L to "")).value
    val products = viewModel.observeProducts(categoryId).collectAsStateWithLifecycle(initialValue = mapOf(0L to "")).value

    Scaffold(
        topBar = { TopBar( title = screenTitle, onNavigateBack) }
    ) {
        if (openDialogBox) {
            DialogBox(dialogText = dialogText) {
                openDialogBox = false
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it)
        ) {
            Dropdown(
                label = "Select a Category",
                options = categories,
                selectedOptionId = categoryId,
                onSelectionChange = {
                    categoryId = it.first
                    productId = 0
                    productName = ""
                })

            Dropdown(
                label = "Select a Product",
                options = products,
                selectedOptionId = productId,
                onSelectionChange = {
                    productId = it.first
                    productName = it.second
                })

            OutlinedTextField(
                value = if (quantity > 0) quantity.toString() else "",
                onValueChange = {
                    quantity = if (it.isNotEmpty()) it.toInt() else 0
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Datepicker("Select Updated Date", initialDate = updatedDate,
                onDateSelected = {  updatedDate = it }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    if (formMode == FormMode.ADD) {
                        val item = ShoppingItemEntity(
                            productId,
                            quantity,
                            updatedDate
                        )
                        viewModel.addItem(item)
                        Toast.makeText(context,
                            "$productName ($quantity) added successfully to the shopping cart",
                            Toast.LENGTH_SHORT).show()

                        // Reset the productId and quantity to enter them again
                        productId = 0L
                        quantity = 0
                    } else {
                        viewModel.selectedShoppingItem?.let {
                            val item = ShoppingItemEntity(
                                it.id, productId,
                                quantity, updatedDate
                            )
                            viewModel.updateItem(item)
                            onNavigateBack()
                        }
                    }
                }) {
                Text(text = confirmButtonLabel)
            }

            Text(text = "You have ${shoppingItemsCount.value} in your Shopping Card")

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoriesAndProductCounts()
                        dialogText = categories.entries.joinToString(separator = "\n") {
                            "${it.key.id} - ${it.key.category} -> ${it.value}"
                        }
                        openDialogBox = true
                    }

                }) {
                Text(text = "Get Categories - Product Count")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getProductCountsPerCategory()
                        dialogText = categories.entries.joinToString(separator = "\n") {
                            "${it.key} -> ${it.value}"
                        }
                        openDialogBox = true
                    }

                }) {
                Text(text = "Get Products Count per Category")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoriesAndProducts()
                        dialogText = categories.entries.joinToString(separator = "\n\n") {
                            "${it.key.category} -> ${it.value.joinToString(prefix = "[", postfix = "]") { "${it.name} ${it.icon}" }}"
                        }
                        openDialogBox = true
                    }

                }) {
                Text(text = "Get Categories - Products")
            }
        }
    }
}