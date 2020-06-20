package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource.LocationDao
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNoteEntity
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.Resource

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

    override fun getAll(): Observable<Resource<List<LocationAndNote>>> {
        return dataSource
            .getAll()
            .map {
                Resource.Success(it)
            }
    }

    override fun getFilteredData(filter: String): Observable<Resource<List<LocationAndNote>>> {
        return dataSource
            .getFilteredData(filter)
            .map {
                Resource.Success(it)
            }
    }

    override fun update(locationAndNote: LocationAndNote): Completable {
        return dataSource
            .update(
                LocationAndNoteEntity(
                locationAndNote.id,
                locationAndNote.latitude,
                locationAndNote.longitude,
                locationAndNote.title,
                locationAndNote.note,
                locationAndNote.created)
            )
    }

    override fun getDataOrderedByDateDesc(filter: String): Observable<Resource<List<LocationAndNote>>> {
        return dataSource
            .orderByDateDesc(filter)
            .map {
                Resource.Success(it)
            }
    }

    override fun getDataOrderedByDateAsc(filter: String): Observable<Resource<List<LocationAndNote>>> {
        return dataSource
            .orderByDateAsc(filter)
            .map {
                Resource.Success(it)
            }
    }

}