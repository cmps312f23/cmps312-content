package oop

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class Faculty(
    firstName: String,
    lastName: String,
    dob: LocalDate,
    val office: String
) : Person(firstName, lastName, dob) {
    override fun toString() = "${super.toString()}. Office: $office"
}