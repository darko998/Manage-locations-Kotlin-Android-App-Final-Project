package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states

import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote

sealed class LocationState {
    object SaveSuccess : LocationState()
    data class SaveError(val error: String) : LocationState()
    data class FetchSuccess(val data: List<LocationAndNote>) : LocationState()
    data class FetchError(val error: String) : LocationState()
    data class FetchLoading(val msg: String) : LocationState()
    data class FetchFilteredSuccess(val data: List<LocationAndNote>) : LocationState()
    data class FetchFilteredError(val error: String) : LocationState()
    object EditSuccess : LocationState()
    data class EditError(val error: String) : LocationState()

}