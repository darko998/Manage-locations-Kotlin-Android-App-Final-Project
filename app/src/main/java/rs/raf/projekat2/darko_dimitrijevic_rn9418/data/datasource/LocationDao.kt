package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.GET
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNoteEntity

@Dao
abstract class LocationDao {

    @Insert
    abstract fun insert(locationAndNoteEntity: LocationAndNoteEntity) : Completable

    @Query("SELECT * FROM location_and_note")
    abstract fun getAll() : Observable<List<LocationAndNote>>

    @Query("DELETE FROM location_and_note WHERE id=:id")
    abstract fun delete(id: Long) : Completable

    @Query("SELECT * FROM location_and_note WHERE title LIKE '%' || :filter || '%' OR note LIKE '%' || :filter || '%'")
    abstract fun getFilteredData(filter: String) : Observable<List<LocationAndNote>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(locationAndNoteEntity: LocationAndNoteEntity) : Completable

    @Query("SELECT * FROM location_and_note WHERE title LIKE '%' || :filter || '%' OR note LIKE '%' || :filter || '%' ORDER BY created DESC")
    abstract fun orderByDateDesc(filter: String) : Observable<List<LocationAndNote>>

    @Query("SELECT * FROM location_and_note WHERE title LIKE '%' || :filter || '%' OR note LIKE '%' || :filter || '%' ORDER BY created ASC")
    abstract fun orderByDateAsc(filter: String) : Observable<List<LocationAndNote>>
}