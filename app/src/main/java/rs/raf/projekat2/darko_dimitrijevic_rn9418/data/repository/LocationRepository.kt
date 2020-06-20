package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource.LocationDatabase
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.Resource

interface LocationRepository {

    fun saveLocation(locationAndNote: LocationAndNote) : Completable
    fun getAll() : Observable<Resource<List<LocationAndNote>>>
    fun getFilteredData(filter: String) : Observable<Resource<List<LocationAndNote>>>
    fun update(locationAndNote: LocationAndNote) : Completable
    fun getDataOrderedByDateDesc(filter: String) : Observable<Resource<List<LocationAndNote>>>
    fun getDataOrderedByDateAsc(filter: String) : Observable<Resource<List<LocationAndNote>>>

}