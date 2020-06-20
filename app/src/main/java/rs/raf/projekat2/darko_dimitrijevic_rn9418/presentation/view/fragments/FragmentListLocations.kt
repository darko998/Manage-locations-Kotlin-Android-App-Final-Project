package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts.LocationContract
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.LocationState
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.activities.LocationEditActivity
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.recycler.LocationAdapter
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.recycler.LocationDiffItemCallback
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels.LocationViewModel

class FragmentListLocations : Fragment(R.layout.fragment_list_locations) {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LocationAdapter
    lateinit var search: EditText
    lateinit var orderBtnDesc: FloatingActionButton
    lateinit var orderBtnAsc: FloatingActionButton

    private val viewModel : LocationContract.ViewModel by sharedViewModel<LocationViewModel>()

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val NOTE = "note"
        const val CREATED = "created"
        const val LAT = "lat"
        const val LNG = "lng"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view != null) {
            init(view)
        }

        return view
    }

    fun init(view: View) {
        initView(view)
        initRecycler()
        initObservers()
        initListeners()
    }

    fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        search = view.findViewById(R.id.et_search)
        orderBtnDesc = view.findViewById(R.id.btn_order_desc)
        orderBtnAsc = view.findViewById(R.id.btn_order_asc)

    }

    fun initListeners() {
        search.doOnTextChanged { text, start, count, after ->
            viewModel.getFilteredData(text.toString())
        }

        /** Listener for DESC floating button. */
        orderBtnDesc.setOnClickListener {
            viewModel.getDataOrderedByDateDesc(search.text.toString())
            orderBtnDesc.visibility = View.GONE
            orderBtnAsc.visibility = View.VISIBLE
        }

        /** Listener for ASC floating button. */
        orderBtnAsc.setOnClickListener {
            viewModel.getDataOrderedByDateAsc(search.text.toString())
            orderBtnAsc.visibility = View.GONE
            orderBtnDesc.visibility = View.VISIBLE
        }
    }

    fun initRecycler() {
        adapter = LocationAdapter(LocationDiffItemCallback()) {
            val intent = Intent(this.context, LocationEditActivity::class.java)
            intent.putExtra(ID, it.id)
            intent.putExtra(TITLE, it.title)
            intent.putExtra(NOTE, it.note)
            intent.putExtra(CREATED, it.created)
            intent.putExtra(LAT, it.latitude)
            intent.putExtra(LNG, it.longitude)

            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            renderState(it)
        })
    }

    fun renderState(state : LocationState) {
        when(state) {
            is LocationState.FetchSuccess -> {
                adapter.submitList(state.data)
            }
            is LocationState.FetchError -> {
                Toast.makeText(this.context, "Error occurred while fetching data.", Toast.LENGTH_SHORT).show()
            }
            is LocationState.FetchFilteredSuccess -> {
                adapter.submitList(state.data)
            }
            is LocationState.FetchFilteredError -> {
                Toast.makeText(this.context, "Error occurred while fetching data.", Toast.LENGTH_SHORT).show()
            }
            is LocationState.EditSuccess -> {
                Toast.makeText(this.context, "Success edit.", Toast.LENGTH_SHORT).show()
            }
            is LocationState.EditError -> {
                Toast.makeText(this.context, "Error: " + state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

}