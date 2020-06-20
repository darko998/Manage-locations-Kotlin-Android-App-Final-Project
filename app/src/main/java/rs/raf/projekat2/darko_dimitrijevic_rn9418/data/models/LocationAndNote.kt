package rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models

data class LocationAndNote(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val note: String,
    val created: Long
)