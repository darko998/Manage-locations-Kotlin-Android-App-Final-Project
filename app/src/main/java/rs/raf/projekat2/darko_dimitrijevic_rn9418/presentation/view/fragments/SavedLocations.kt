package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_saved_locations.*
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.adapters.TabPageAdapter

class SavedLocations : Fragment(R.layout.fragment_saved_locations) {

    lateinit var myContext : FragmentActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myContext = activity as FragmentActivity

        init()
    }


    fun init() {
        initTab()
    }

    fun initTab() {
        myContext.tab_view_pager.adapter = TabPageAdapter(childFragmentManager)
        myContext.tab_layout.setupWithViewPager(myContext.tab_view_pager)
    }
}