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
import shopapp.entity.Product
import shopapp.entity.ShoppingItem
import shopapp.entity.User
import shopapp.repository.ShoppingRepository

/* Every time you change your entity classes, you must change the DB version
   otherwise you will get an exception.
   When the version changes the DB will be dropped and recreated
 */
@Database(entities = [Product::class, Category::class, ShoppingItem::class, User::class],
    version = 1)
@TypeConverters(DateConverter::class)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
    abstract fun productDao(): ProductDao

    // Create a singleton dbInstance
    companion object {
        private var db: ShoppingDB? = null

        fun getInstance(context: Context): ShoppingDB {
            if (db == null) {
                // Use Room.databaseBuilder to open( or create) the database
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
