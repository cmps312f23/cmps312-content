package shopapp.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import shopapp.view.screens.ShoppingListItem
import shopapp.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(onAddItem: () -> Unit, onEditItem: () -> Unit) {
    /* Get an instance of the shared viewModel
        Make the activity the store owner of the viewModel
        to ensure that the same viewModel instance is used for all destinations
    */
    val viewModel =
        viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val shoppingList = viewModel.shoppingList //.observeAsState()
    //val shoppingItemsCount = viewModel.shoppingItemsCount.observeAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Shopping List (${shoppingList.size})",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.selectedShoppingItem = null
                    onAddItem()
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                },
                //backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(it)) {
            items(shoppingList) { shoppingItem ->
                ShoppingListItem(
                    shoppingItem,
                    onEditItem = {
                        viewModel.selectedShoppingItem = it
                        onEditItem()
                    },
                    onEditQuantity = {
                        viewModel.updateQuantity(it)
                    },
                    onDeleteItem = {
                        viewModel.deleteItem(it)
                        // Show a snack bar and allow the user to undo delete
                        scope.launch {
                            val snackBarResult = snackbarHostState.showSnackbar(
                                    message = "${it.productName} deleted",
                                    actionLabel = "Undo",
                                )
                            if (snackBarResult == SnackbarResult.ActionPerformed) {
                                // Undo delete shopping item
                                viewModel.addItem(it)
                            }
                        }
                    }
                )
            }
        }
    }
}