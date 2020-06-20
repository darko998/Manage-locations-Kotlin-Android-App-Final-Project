package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.LocationState

interface LocationContract {
    interface ViewModel {
        val state: LiveData<LocationState>

        fun saveLocation(locationAndNote: LocationAndNote)
        fun getSavedLocations()
        fun getFilteredData(filter: String)
        fun update(locationAndNote: LocationAndNote)
        fun getDataOrderedByDateDesc(filter: String)
        fun getDataOrderedByDateAsc(filter: String)

    }
}