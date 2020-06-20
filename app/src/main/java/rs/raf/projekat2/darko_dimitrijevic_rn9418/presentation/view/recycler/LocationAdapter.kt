package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote

class LocationAdapter(locationDiffItemCallback: LocationDiffItemCallback, val clicked: (LocationAndNote) -> Unit) : ListAdapter<LocationAndNote, LocationViewHolder>(locationDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.location_list_item, parent, false)

        return LocationViewHolder(containerView) {
            val locationAndNote = getItem(it)

            clicked.invoke(locationAndNote)
        }
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val locationAndNote = getItem(position)

        holder.bind(locationAndNote)
    }

}