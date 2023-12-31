package shopapp.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import shopapp.entity.Category
import shopapp.entity.ProductEntity
import shopapp.entity.ShoppingItemEntity
import shopapp.entity.User
import shopapp.repository.ShoppingRepository

/* Every time you change your entity classes, you must change the DB version
   otherwise you will get an exception.
   When the version changes the DB will be dropped and recreated
 */
@Database(entities = [ProductEntity::class, Category::class, ShoppingItemEntity::class, User::class],
    version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
    abstract fun productDao(): ProductDao

    // Create a singleton dbInstance
    companion object {
        private var db: ShoppingDB? = null

        fun getInstance(context: Context): ShoppingDB {
            if (db == null) {
                // Use Room.databaseBuilder to open the database
                // (DB will be created if it does NOT exist)
                db = Room.databaseBuilder(
                    context,
                    ShoppingDB::class.java, "shoppingDB"
                ).fallbackToDestructiveMigration().build()
            }
            // After the DB instance is created load the data from json files
            // into the Category and Product tables if the Category table is empty
            CoroutineScope(Dispatchers.IO).launch {
                 ShoppingRepository.initDB(db, context)
            }
            return db as ShoppingDB
        }
    }
}
