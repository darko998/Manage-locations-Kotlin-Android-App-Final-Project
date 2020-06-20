package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import java.util.*

class LocationViewHolder(private val containerView: View, val clicked: (Int) -> Unit) : RecyclerView.ViewHolder(containerView) {

    lateinit var title: TextView
    lateinit var note: TextView
    lateinit var created: TextView

    init {
        initView()
        initListeners()
    }

    fun initListeners() {
        containerView.setOnClickListener {
            clicked.invoke(adapterPosition)
        }
    }

    fun initView() {
        title = containerView.findViewById(R.id.tv_title)
        note = containerView.findViewById(R.id.tv_note)
        created = containerView.findViewById(R.id.tv_created)
    }

    fun bind(locationAndNote: LocationAndNote) {
        title.text = locationAndNote.title
        note.text = locationAndNote.note
        created.text = parseDateForDisplay(locationAndNote.created)
    }

    fun parseDateForDisplay(milliseconds: Long) : String {
        val dateString = Date(milliseconds).toString()
        val dateParts = dateString.split(" ")

        return dateParts[0] + " " + dateParts[1] + " " + dateParts[2] + " " + dateParts[3]
    }
}