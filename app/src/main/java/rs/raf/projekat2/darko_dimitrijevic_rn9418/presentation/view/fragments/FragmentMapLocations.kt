package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts.LocationContract
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.LocationState
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.cluster.Cluster
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels.LocationViewModel
import timber.log.Timber


class FragmentMapLocations : Fragment(R.layout.fragment_map_locations), OnMapReadyCallback {

    private lateinit var myContext: FragmentActivity
    private val viewModel : LocationContract.ViewModel by sharedViewModel<LocationViewModel>()

    var mCurrLocationMarker: Marker? = null
    private var mMap: GoogleMap? = null
    lateinit var mClusterManager : ClusterManager<Cluster>


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity as FragmentActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        myContext = activity as FragmentActivity
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun init() {
        initObservers()
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            renderState(it)
        })

        viewModel.getSavedLocations()
    }

    private fun renderState(state : LocationState)  {
        when(state) {
            is LocationState.FetchSuccess -> {
                putMarkers(state.data)
            }
            is LocationState.FetchError -> Toast.makeText(myContext, "Error: " + state.error, Toast.LENGTH_SHORT).show()
        }
    }

    /** This method put markers on map, with cluster enabled. */
    private fun putMarkers(locationsAndNotes: List<LocationAndNote>) {

        if(locationsAndNotes.size < 1) {
            return
        }

        mClusterManager.clearItems()
        val builder = LatLngBounds.Builder()

        for (location in locationsAndNotes) {
            val latLng = LatLng(location.latitude, location.longitude)
            builder.include(latLng)

            val offsetItem = Cluster(latLng.latitude, latLng.longitude, location.title, location.note)
            mClusterManager.addItem(offsetItem)
        }


        /** Here we need bounds because we want to zoom so that all marks are visible.
         * Padding is 600, because we want to see cluster that is implemented. */
        val bounds : LatLngBounds = builder.build()
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 600))
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true

        setUpCluster()
    }


    private fun setUpCluster() {
        mClusterManager = ClusterManager(myContext, mMap)
        mMap?.setOnCameraIdleListener(mClusterManager)
        mMap?.setOnMarkerClickListener(mClusterManager)
    }


}