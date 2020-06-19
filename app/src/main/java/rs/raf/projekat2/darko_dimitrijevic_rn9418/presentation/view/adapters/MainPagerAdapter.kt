package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments.LocationFragment
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments.SavedLocations

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val ITEM_COUNT = 2
        const val LOCATION_FRAGMENT = 0
        const val LOCATIONS_LIST_FRAGMENT = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            LOCATION_FRAGMENT -> LocationFragment()
            LOCATIONS_LIST_FRAGMENT -> SavedLocations()
            else -> LocationFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

}