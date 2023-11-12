package shopapp.view

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
import shopapp.entity.ShoppingItem
import shopapp.view.components.Datepicker
import shopapp.view.components.Dropdown
import shopapp.view.components.TopBar
import shopapp.viewmodel.ShoppingViewModel

enum class FormMode { ADD, EDIT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun ShoppingItemScreen(shoppingItemId: Long? = null, onNavigateBack: () -> Unit) {
fun ShoppingItemScreen(onNavigateBack: () -> Unit) {
    var formMode = FormMode.ADD
    var screenTitle = "Add Shopping Item"
    var confirmButtonLabel = "Add"

    val viewModel = viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val shoppingItemsCount = viewModel.shoppingItemsCountFlow.collectAsStateWithLifecycle(initialValue = 0)

    var categoryId by remember { mutableStateOf(viewModel.selectedShoppingItem?.categoryId ?: 0) }
    var productId by remember { mutableStateOf(viewModel.selectedShoppingItem?.productId ?: 0) }
    var quantity by remember { mutableStateOf( viewModel.selectedShoppingItem?.quantity ?:0) }

    val coroutineScope = rememberCoroutineScope()

/*
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )

    var showDatePicker by remember {
        mutableStateOf(false)
    }*/

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var updatedDate by remember { mutableStateOf( viewModel.selectedShoppingItem?.updatedDate ?: today) }


    // In case of Edit Mode get the Shopping Item to edit
    if (viewModel.selectedShoppingItem != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Shopping Item"
        confirmButtonLabel = "Update"
    }

    val categories = viewModel.categoriesMapFlow.collectAsStateWithLifecycle(initialValue = mapOf(0L to "")).value
    // Every time categories change ->
    //      Convert a list to a map needed to fill the categories dropdown
    /*val categoryOptions by remember {
        derivedStateOf {
            categories.value?.associate {
                Pair(it.id, it.name)
            }
        }
    }*/

    // Every time categoryId change get the products of the selected category
    var products = viewModel.observeProducts(categoryId).collectAsStateWithLifecycle(initialValue = mapOf(0L to "")).value
    // Every time products change ->
    //      Convert a list to a map needed to fill the products dropdown
    /*val productOptions by remember {
        derivedStateOf {
            products.value?.associate {
                Pair(it.id, "${it.name} ${it.icon}")
            }
        }
    }*/

    Scaffold(
        topBar = { TopBar( title = screenTitle, onNavigateBack) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it)
        ) {
            Dropdown(
                label = "Select a Category",
                options = categories,
                selectedOptionId = categoryId,
                onSelectionChange = {
                    categoryId = it
                })

            Dropdown(
                label = "Select a Product",
                options = products,
                selectedOptionId = productId,
                onSelectionChange = { productId = it.toLong() })

            OutlinedTextField(
                value = if (quantity > 0) quantity.toString() else "",
                onValueChange = {
                    quantity = if (it.isNotEmpty()) it.toInt() else 0
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            /*updatedDate = datePickerState.selectedDateMillis?.let {
                millisecondsToLocalDate(it).date
            } ?: today

            // Second, you simply have to add the DatePicker component to your layout.
            DatePicker(state = datePickerState,
                showModeToggle = true, // allow input mode or picker
                title = { Text(text = "Select Updated Date") },
                headline = {
                    // You need to look the datePickerState value
                    Text(
                        text = datePickerState.displayMode.toString()
                    )
                },

            )

            //val formattedDate = "${updatedDate.dayOfMonth}/${updatedDate.month}.${updatedDate.year}"

            // Finally, to get the user value you could do something like this:
            //Text("Selected: $formattedDate")
*/
            Datepicker("Select Updated Date", initialDate = updatedDate,
                onDateSelected = {  updatedDate = it }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    if (formMode == FormMode.ADD) {
                        val item = ShoppingItem(
                            productId = productId,
                            quantity = quantity,
                            updatedDate = updatedDate
                        )
                        viewModel.addItem(item)
                        // Reset the productId and quantity to enter them again
                        productId = 0L
                        quantity = 0
                    } else {
                        viewModel.selectedShoppingItem?.let {
                            it.productId = productId
                            it.quantity = quantity
                            it.updatedDate = updatedDate
                            viewModel.updateItem(it)
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
                        categories.entries.forEach {
                            println("${it.key.name} -> ${it.value}")
                        }
                    }

                }) {
                Text(text = "Get Categories - Product Count")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoryNamesAndProductCounts()
                        categories.entries.forEach {
                            println("${it.key} -> ${it.value}")
                        }
                    }

                }) {
                Text(text = "Get Category names - Product Count")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoriesAndProducts()
                        categories.entries.forEach {
                            println("${it.key.name} -> ${it.value}")
                        }
                    }

                }) {
                Text(text = "Get Categories - Products")
            }
        }
    }
}