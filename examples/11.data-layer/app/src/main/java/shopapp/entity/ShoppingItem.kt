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

@Entity
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var productId: Long,
    var quantity: Int,
    /*
    Unfortunate limitation in Room:
      If productName is annotated with @Ignore Room will ignore when writing 👍 but
      it will also ignore it when reading👎 even if productName column is explicitly
      returned in a join query.
      So, as a workaround we store it as a null value but never read it
    */
    //@Ignore // productName will NOT be stored in the database
    var productName: String? = null,
    var categoryId: Long? = null,

    /* Need to add TypeConverter otherwise you get compile time error
        Cannot figure out how to read/save this field into database
     */
     // More info about Kotlin DateTime @ https://androidrepo.com/repo/Kotlin-kotlinx-datetime-android-date-time
     var updatedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
) {
    constructor(productId: Long, quantity: Int) : this(0, productId, quantity)
    constructor(productId: Long, quantity: Int, updatedDate: LocalDate) : this(0, productId, quantity, updatedDate = updatedDate)
}

/*
    insert into ShoppingItem(productId, quantity, updatedDate) values (1, 20, 46464)
    insert into ShoppingItem(productId, quantity, updatedDate) values (2, 30, 66464)
*/
