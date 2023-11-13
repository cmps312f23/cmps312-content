package shopapp.entity

import kotlinx.serialization.Serializable

// Product used for reading products from the Product table with
// additional category property
@Serializable
data class Product(
    var id: Long = 0,
    var name: String,
    var icon: String,
    var categoryId: Long = 0,
    var category: String
)

/* -- Inheritance attempt - serializable did NOT work
@Serializable
class Product (id: Long, name: String, image: String, categoryId: Long, var category: String = "")
    : ProductEntity(id, name, image, categoryId) {
        constructor () : this(0, "", "",0, "")
        /*constructor (id: Long, name: String, image: String, categoryId: Long, category: String)
                : this(id, name, image, categoryId) {
            this.category = category
        }*/
}
*/

