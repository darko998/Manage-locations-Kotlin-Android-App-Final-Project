package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.SaveLocationState

interface LocationContract {
    interface ViewModel {
        val saveDone: LiveData<SaveLocationState>

        fun saveLocation(locationAndNote: LocationAndNote)
    }
}