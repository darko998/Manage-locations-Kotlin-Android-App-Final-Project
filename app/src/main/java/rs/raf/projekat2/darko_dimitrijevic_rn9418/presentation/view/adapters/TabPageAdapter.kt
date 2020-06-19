package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments.FragmentListLocations
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.fragments.FragmentMapLocations

class TabPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val ITEM_COUNT = 2
        const val MAP = 0
        const val LIST = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            MAP -> FragmentMapLocations()
            LIST -> FragmentListLocations()
            else -> FragmentMapLocations()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            MAP -> "Map"
            LIST -> "List"
            else -> "Map"
        }
    }

}