package shopapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import shopapp.entity.ShoppingItem

@Composable
fun ShoppingListItem(shoppingItem: ShoppingItem,
                     onEditItem: (item: ShoppingItem) -> Unit,
                     onEditQuantity: (item: ShoppingItem) -> Unit,
                     onDeleteItem: (item: ShoppingItem) -> Unit
                    ) {
    var quantity by remember { mutableStateOf(0) }
    quantity = shoppingItem.quantity
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 8.dp, end =8.dp)
    ) {
        shoppingItem.productName?.let {
            Text(text = it,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1F)
            )
        }
        Row(modifier = Modifier.weight(1F)) {
            IconButton(
                onClick = {
                    shoppingItem.quantity++
                    quantity = shoppingItem.quantity
                    onEditQuantity(shoppingItem)
                }) {
                Icon(
                    Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Increase",
                )
            }
            Text(
                text = quantity.toString(),
                modifier = Modifier.padding(top = 8.dp)
            )
            IconButton(
                onClick = {
                    shoppingItem.quantity--
                    quantity = shoppingItem.quantity
                    onEditQuantity(shoppingItem)
                }) {
                Icon(
                    Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Decrease",
                )
            }

            IconButton(
                onClick = {
                    onEditItem(shoppingItem)
                    //onEditItem(shoppingItem.id)
                }) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "Edit",
                )
            }

            IconButton(
                onClick = {
                    onDeleteItem(shoppingItem)
                }) {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "Delete",
                )
            }
        }
    }
}