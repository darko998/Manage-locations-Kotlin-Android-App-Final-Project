package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_and_note")
data class LocationAndNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val note: String,
    val created: Long
)