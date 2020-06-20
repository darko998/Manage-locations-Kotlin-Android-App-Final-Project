package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class Cluster : ClusterItem {
    private val mPosition: LatLng
    private val mTitle: String
    private val mSnippet: String

    constructor(lat: Double, lng: Double) {
        mPosition = LatLng(lat, lng)
        mTitle = ""
        mSnippet = ""
    }

    constructor(lat: Double, lng: Double, title: String, snippet: String) {
        mPosition = LatLng(lat, lng)
        mTitle = title
        mSnippet = snippet
    }

    override fun getPosition(): LatLng {
        return mPosition
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSnippet(): String {
        return mSnippet
    }

}