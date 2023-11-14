package shopapp.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

// ProductEntity maps to the Product table.
// It does not have the category property
@Serializable
@Entity(tableName = "Product",
    foreignKeys = [ForeignKey(entity = Category::class,
        parentColumns = ["id"], // Primary of the 1-side table
        childColumns = ["categoryId"], // Foreign key from the many-side table
        onDelete = ForeignKey.CASCADE)],
    // Create an index on the categoryId column to speed-up query execution
    indices = [Index(value = ["categoryId"])])
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var icon: String,
    var categoryId: Long
) {
   constructor(name: String, image: String, categoryId: Long)
            : this(0, name, image, categoryId)

    override fun toString(): String {
        return "$name $icon"
    }
}