package com.dev.librorum

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.View
import com.dev.librorum.customViews.SectionsPagerAdapter
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity(),
        RecommendedFragment.OnFragmentInteractionListener,
        SortFragment.OnFragmentInteractionListener,
        SortedFragment.OnFragmentInteractionListener, DBWrapper.DbInteraction{
    private lateinit var sortFragment: SortFragment
    private lateinit var sortedFragment: SortedFragment
    private lateinit var recommendedFragment: RecommendedFragment
    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var db: DBHandler
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.setCurrentItem(0, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
//                header.text = getString(R.string.nav_title_info)
                viewPager.setCurrentItem(1, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                header.text = getString(R.string.nav_title_profile)
                viewPager.setCurrentItem(2, false)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onDbLoaded() {
        Log.d("Librorum", "db loaded")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Librorum", "Main")
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        sortFragment = SortFragment()
        sortedFragment = SortedFragment()
        recommendedFragment = RecommendedFragment()

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,
                arrayOf(sortedFragment, sortFragment, recommendedFragment))
        viewPager.adapter = mSectionsPagerAdapter // косяк тут
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                navigation.selectedItemId  = when (position) {
                    0 -> R.id.navigation_home
                    1 -> R.id.navigation_dashboard
                    2 -> R.id.navigation_notifications
                    else -> throw IndexOutOfBoundsException("Wrong!!!")
                }
            }
        })
        Log.d("Librorum", "Created")
        doAsync {
            DBWrapper.registerCallback(this@MainActivity, "LoginActivity")
            DBWrapper.initDb(applicationContext, resources)
            db = DBWrapper.getInstance(this@MainActivity)
            Log.d("Librorum", "Successfully loaded db")
        }
    }


    override fun onFragmentInteraction(uri: Uri) {}
}
