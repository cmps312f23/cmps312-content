package shopapp.entity

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class ShoppingItem(
    @DocumentId
    val id: String = "",
    var productId: String,
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
    var categoryId: String? = null,

    //val updatedDate: Date = java.util.Calendar.getInstance().time,
    /* Need to add TypeConverter otherwise you get compile time error
        Cannot figure out how to read/save this field into database
    */
    // More info about Kotlin DateTime @ https://androidrepo.com/repo/Kotlin-kotlinx-datetime-android-date-time
    @ServerTimestamp
    val updatedDate: Date = java.util.Calendar.getInstance().time,
    //val updatedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,

    var uid : String? = null) {
    // Required by Firebase deserializer otherwise you get exception 'does not define a no-argument constructor'
    constructor(): this("", "", 0)

    constructor(productId: String, quantity: Int) : this("", productId, quantity)
}