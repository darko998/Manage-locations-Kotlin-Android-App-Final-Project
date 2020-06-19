package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts.LocationContract
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.SaveLocationState
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels.LocationViewModel
import java.io.IOException
import java.util.*

class FragmentMapLocations : Fragment(R.layout.fragment_map_locations), OnMapReadyCallback {

    private lateinit var myContext: FragmentActivity
    private val viewModel : LocationContract.ViewModel by sharedViewModel<LocationViewModel>()

    var mCurrLocationMarker: Marker? = null
    private var mMap: GoogleMap? = null

    lateinit var currLocation : LatLng

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity as FragmentActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myContext = activity as FragmentActivity
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true



        //Showing Current Location Marker on Map
        val latLng = LatLng(43.84374804170782, 19.90413828922751)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)

        val longitude = latLng.longitude
        val latitude = latLng.latitude
        currLocation = LatLng(latitude, longitude)
        val geocoder = Geocoder(activity?.applicationContext,
            Locale.getDefault())
        try {
            val listAddresses = geocoder.getFromLocation(latitude,
                longitude, 1)
            if (null != listAddresses && listAddresses.size > 0) {
                val state = listAddresses[0].adminArea
                val country = listAddresses[0].countryName
                val subLocality = listAddresses[0].subLocality
                markerOptions.title("" + latLng + "," + subLocality + "," + state
                        + "," + country)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mCurrLocationMarker = mMap?.addMarker(markerOptions)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
    }




}