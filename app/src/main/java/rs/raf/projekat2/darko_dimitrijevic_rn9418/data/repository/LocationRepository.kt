package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.repository

import io.reactivex.Completable
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.datasource.LocationDatabase
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote

interface LocationRepository {

    fun saveLocation(locationAndNote: LocationAndNote) : Completable
}