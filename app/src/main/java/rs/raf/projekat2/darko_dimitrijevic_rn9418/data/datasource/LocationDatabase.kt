package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNoteEntity

@Database(
    entities = [LocationAndNoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters()
abstract class LocationDatabase : RoomDatabase() {
    abstract fun getDao(): LocationDao
}