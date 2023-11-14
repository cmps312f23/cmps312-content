package shopapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/*  (foreignKeys = [ForeignKey(entity = Product::class,
    parentColumns = ["id"],
    childColumns = ["productId"],
    onDelete = ForeignKey.CASCADE)],
    // Create an index on the productId column to speed-up query execution
    indices = [Index(value = ["productId"])]) */

@Entity(tableName = "ShoppingItem")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var productId: Long,
    //var categoryId: Long,
    var quantity: Int,
    /* Need to add TypeConverter otherwise you get compile time error
        Cannot figure out how to read/save this field into database
     */
     var updatedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
) {
    constructor(productId: Long, quantity: Int) : this(0, productId, quantity)
    constructor(productId: Long, quantity: Int, updatedDate: LocalDate) : this(0, productId, quantity, updatedDate)
}

/*
    insert into ShoppingItem(productId, quantity, updatedDate) values (1, 20, 46464)
    insert into ShoppingItem(productId, quantity, updatedDate) values (2, 30, 66464)
*/
