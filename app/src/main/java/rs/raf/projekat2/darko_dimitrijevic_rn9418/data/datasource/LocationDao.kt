package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Completable
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNoteEntity

@Dao
abstract class LocationDao {

    @Insert
    abstract fun insert(locationAndNoteEntity: LocationAndNoteEntity) : Completable
}