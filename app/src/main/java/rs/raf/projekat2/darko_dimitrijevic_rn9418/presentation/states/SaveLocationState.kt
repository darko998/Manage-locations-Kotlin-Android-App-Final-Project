package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states

sealed class SaveLocationState {
    object Success : SaveLocationState()
    data class Error(val error: String) : SaveLocationState()
}