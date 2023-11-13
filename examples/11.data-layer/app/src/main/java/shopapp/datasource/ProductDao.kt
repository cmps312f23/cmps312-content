package shopapp.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import shopapp.entity.Category
import shopapp.entity.Product
import shopapp.entity.ProductEntity

@Dao
interface ProductDao {
    // Product related methods
    @Query("""
        Select p.id, p.name, p.icon, p.categoryId, c.categoryName category 
        From Product p join Category c on p.categoryId = c.categoryId 
        Where p.categoryId = :categoryId order by p.name 
        """)
    suspend fun getProducts(categoryId: Long): List<Product>

    // Returns a map needed to fill a Products Dropdown
    @Query("""
        Select p.id, (p.name || ' ' || p.icon) as name From Product p
        Where p.categoryId = :categoryId order by p.name 
        """)
    fun observeProducts(categoryId: Long) : Flow<Map<@MapColumn(columnName = "id") Long,
                                                    @MapColumn(columnName = "name") String>>

    @Insert
    suspend fun insertProducts(products: List<ProductEntity>) : List<Long>

    // Category related methods
    @Query("select * from Category order by categoryName")
    fun observeCategories() : Flow<List<Category>>

    // Returns a map needed to fill a Categories Dropdown
    @Query("select c.categoryId, c.categoryName from Category c order by categoryName")
    fun observeCategoriesMap() : Flow<Map<@MapColumn(columnName = "categoryId") Long,
                                      @MapColumn(columnName = "categoryName") String>>

    @Query("select * from Category where categoryName = :name")
    suspend fun getCategory(name: String) : Category?

    @Query("select count(*) from Category")
    suspend fun getCategoryCount() : Int

    // Deletes rows in the db matching the specified [ids]
    @Query(
        value = """
            DELETE FROM Product
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteProducts(ids: List<String>)


    //@Upsert // Or you can use Upsert to insert or update if already exists
    @Insert
    suspend fun addCategories(categories: List<Category>) : List<Long>

    @Query("""
       Select c.*, p.*, c.categoryName category
       From Category c join Product p on c.categoryId = p.categoryId
       """)
    suspend fun getCategoriesAndProducts(): Map<Category, List<Product>>

    // @MapColumn is required when the key or value column of
    // the map are from a single column
    @Query("""
       Select c.*, count(p.id) as productCount
       From Category c join Product p on c.categoryId = p.categoryId
       Group by c.categoryId
       """)
    suspend fun getCategoriesAndProductCounts(): Map<Category,
                                                   @MapColumn(columnName = "productCount") Int>

    @Query("""
       Select c.categoryName, count(p.id) as productCount
       From Category c join Product p on c.categoryId = p.categoryId
       Group by c.categoryName
       """)
    suspend fun getCategoryNamesAndProductCounts(): Map<@MapColumn(columnName = "categoryName") String,
                                                        @MapColumn(columnName = "productCount") Long>
}