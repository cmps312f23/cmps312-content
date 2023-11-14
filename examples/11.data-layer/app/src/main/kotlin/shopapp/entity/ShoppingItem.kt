package shopapp.entity

import kotlinx.datetime.LocalDate

data class ShoppingItem(
    val id: Long,
    var productId: Long,
    var quantity: Int,
    var updatedDate: LocalDate,
    var productName: String,
    var categoryId: Long,
    var category: String,
) {
    fun toShoppingItemEntity() = ShoppingItemEntity(this.id,
        this.productId, this.quantity, this.updatedDate)
}