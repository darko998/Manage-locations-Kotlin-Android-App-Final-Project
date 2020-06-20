package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.states.LocationState
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels.LocationViewModel
import timber.log.Timber
import java.io.IOException
import java.util.*

class LocationFragment : Fragment(R.layout.fragment_location), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener,	GoogleMap.OnMapClickListener {

    private lateinit var myContext: FragmentActivity
    private val viewModel : LocationContract.ViewModel by sharedViewModel<LocationViewModel>()

    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mLocationRequest: LocationRequest? = null
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

        init()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }

        myContext = activity as FragmentActivity
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun init() {
        initListeners()
        initObservers()
    }

    fun initObservers() {

        /** Here we listen on changes in saveDone object, which we created to monitor saving state.
         * If saving was successful state will be Success, if not, it will be Error. */
        viewModel.state.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            renderState(it)
        })
    }

    /** This method just render Toast message depending on the saveDone state. */
    private fun renderState(state: LocationState) {
        when(state) {
            is LocationState.SaveSuccess -> {
                Toast.makeText(myContext, "You successfully saved location and note!", Toast.LENGTH_SHORT).show()
            }
            is LocationState.SaveError -> {
                Toast.makeText(myContext, "Error: " + state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun initListeners() {

        /** Here we set listener for SAVE button. */
        myContext.btn_save.setOnClickListener {
            val title = myContext.et_title.text.toString()
            val note = myContext.et_note.text.toString()

            val locationAndNote = LocationAndNote(0, currLocation.latitude, currLocation.longitude, title, note, Date().time)

            viewModel.saveLocation(locationAndNote)

            /** Reset edit texts to "" */
            myContext.et_title.setText("")
            myContext.et_note.setText("")

        }

        /** Here we set listener for CLOSE button. */
        myContext.btn_close.setOnClickListener {
            myContext.finish()
        }

    }

    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(myContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(myContext, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            } else {
                ActivityCompat.requestPermissions(myContext, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            }
            false
        } else {
            true
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap?.isMyLocationEnabled = true
            } else {
                Toast.makeText(this.context, "This app features are not available if you don't wan't to give LOCATION permission.", Toast.LENGTH_LONG).show()
            }
        } else {
            buildGoogleApiClient()
            mMap?.isMyLocationEnabled = true
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(myContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mGoogleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest?.setInterval(5000)
        mLocationRequest?.setFastestInterval(1000)
        mLocationRequest?.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        mMap?.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this.context, "permission denied",
                        Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location) {

        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker?.remove()
        }
        //Showing Current Location Marker on Map
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        val locationManager = myContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), true)
        if (ActivityCompat.checkSelfPermission(myContext,
                Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED) {
            return
        }
        val locations = locationManager.getLastKnownLocation(provider)
        val providerList = locationManager.allProviders
        if (locations != null && providerList != null && providerList.size > 0) {
            val longitude = locations.longitude
            val latitude = locations.latitude
            currLocation = LatLng(latitude, longitude)
            val geocoder = Geocoder(activity?.applicationContext,
                Locale.getDefault())
            try {
                val listAddresses = geocoder.getFromLocation(latitude,
                    longitude, 1)
                if (null != listAddresses && listAddresses.size > 0) {
                    val country = listAddresses[0].countryName
                    markerOptions.title("" + latLng + "," + country)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            /** If we can't get location for some reason, we set some default location.
             * Here default location will be in place Sevojno, Serbia. */
            val longitude = 19.90413828922751
            val latitude = 43.84374804170782
            currLocation = LatLng(latitude, longitude)
            val geocoder = Geocoder(activity?.applicationContext,
                Locale.getDefault())
            try {
                val listAddresses = geocoder.getFromLocation(latitude,
                    longitude, 1)
                if (null != listAddresses && listAddresses.size > 0) {
                    val country = listAddresses[0].countryName
                    markerOptions.title("" + latLng + "," + country)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mCurrLocationMarker = mMap?.addMarker(markerOptions)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }

    }

    override fun onMapClick(p0: LatLng?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}