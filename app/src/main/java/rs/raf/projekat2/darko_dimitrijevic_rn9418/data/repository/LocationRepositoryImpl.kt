package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository

import io.reactivex.Completable
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource.LocationDao
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNoteEntity

class LocationRepositoryImpl (val dataSource: LocationDao) : LocationRepository {

    override fun saveLocation(locationAndNote: LocationAndNote): Completable {
        val locationAndNoteEntity = LocationAndNoteEntity(
            0,
            locationAndNote.latitude,
            locationAndNote.longitude,
            locationAndNote.title,
            locationAndNote.note,
            locationAndNote.created)

        return dataSource.insert(locationAndNoteEntity)
    }

}