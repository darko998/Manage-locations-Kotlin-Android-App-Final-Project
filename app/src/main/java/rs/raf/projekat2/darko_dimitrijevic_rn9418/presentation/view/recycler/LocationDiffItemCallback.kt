package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.recycler

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote

class LocationDiffItemCallback : DiffUtil.ItemCallback<LocationAndNote>() {

    override fun areItemsTheSame(oldItem: LocationAndNote, newItem: LocationAndNote): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationAndNote, newItem: LocationAndNote): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.note == newItem.note &&
                oldItem.created == newItem.created
    }

}