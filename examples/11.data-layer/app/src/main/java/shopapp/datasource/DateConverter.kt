package shopapp.datasource

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class DateConverter {
    // Convert from Date to a value that can be stored in SQLite Database
    @TypeConverter
    fun fromDate(date: LocalDate) : Long =
        date.atTime(0,0,0 ).toInstant(TimeZone.currentSystemDefault()).epochSeconds

    // Convert from date Long value read from SQLite DB to a Date value
    // that can be assigned to an entity property
    @TypeConverter
    fun toDate(dateLong: Long) : LocalDate =
        Instant.fromEpochSeconds(dateLong).toLocalDateTime(TimeZone.currentSystemDefault()).date
}