package rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import rs.raf.projekat2.darko_dimitrijevic_rn9418.R
import rs.raf.projekat2.darko_dimitrijevic_rn9418.presentation.view.adapters.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
        initViewPager()
        initBottomNav()
    }

    fun initViewPager() {
        main_view_pager.adapter = MainPagerAdapter(supportFragmentManager)
    }

    fun initBottomNav() {
        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icon_location -> {
                    main_view_pager.setCurrentItem(MainPagerAdapter.LOCATION_FRAGMENT, false)
                }
                R.id.icon_list -> {
                    main_view_pager.setCurrentItem(MainPagerAdapter.LOCATIONS_LIST_FRAGMENT, false)
                }
                else -> {
                    main_view_pager.setCurrentItem(MainPagerAdapter.LOCATION_FRAGMENT, false)
                }
            }

            true
        }
    }
}
