package shopapp.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Address(val buildingNo: String, val street: String, val city: String)

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val firstName: String,
    val lastName: String,
    @Embedded val address: Address)
