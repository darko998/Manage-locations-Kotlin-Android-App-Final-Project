package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_edit_location.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.data.models.LocationAndNote
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.contracts.LocationContract
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.cluster.Cluster
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments.FragmentListLocations
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.viewmodels.LocationViewModel
import timber.log.Timber

class LocationEditActivity : AppCompatActivity(R.layout.activity_edit_location), OnMapReadyCallback {

    private val viewModel : LocationContract.ViewModel by viewModel<LocationViewModel>()

    lateinit var btnEdit: Button
    lateinit var btnClose: Button

    lateinit var locationAndNote: LocationAndNote

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    fun init() {
        initView()
        initListeners()
    }

    fun initView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_edit) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnEdit = findViewById(R.id.btn_edit)
        btnClose = findViewById(R.id.btn_close)

        val intent = intent
        val id = intent.getLongExtra(FragmentListLocations.ID, -1)
        val title = intent.getStringExtra(FragmentListLocations.TITLE)
        val note = intent.getStringExtra(FragmentListLocations.NOTE)
        val created = intent.getLongExtra(FragmentListLocations.CREATED, -1)
        val lat = intent.getDoubleExtra(FragmentListLocations.LAT, -1.0)
        val lng = intent.getDoubleExtra(FragmentListLocations.LNG, -1.0)

        locationAndNote = LocationAndNote(id, lat, lng, title, note, created)

        /** Fill in fields title and note */
        et_title.setText(title.toString())
        et_note.setText(note.toString())
    }

    fun initListeners() {
        btnEdit.setOnClickListener {
            val tmpLocationAndNote = LocationAndNote(
                locationAndNote.id,
                locationAndNote.latitude,
                locationAndNote.longitude,
                et_title.text.toString(),
                et_note.text.toString(),
                locationAndNote.created)

            viewModel.update(tmpLocationAndNote)
            finish()
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true


        /** Set up mark on map */
        val marker = MarkerOptions()
        marker.position(LatLng(locationAndNote.latitude, locationAndNote.longitude))
        marker.title("Title: " + locationAndNote.title + "  Note: " + locationAndNote.note)
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap?.addMarker(marker)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(locationAndNote.latitude, locationAndNote.longitude), 11f))
    }

}