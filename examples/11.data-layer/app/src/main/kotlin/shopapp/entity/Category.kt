package shopapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    // Added column names to avoid confusion when joining Category and Product tables
    //@ColumnInfo(name = "categoryId")
    val id: Long = 0,
    //@ColumnInfo(name = "category")
    val category: String
) {
    override fun toString(): String {
        return category
    }
}