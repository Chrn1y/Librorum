package com.dev.librorum

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.View
import android.widget.Button
import com.dev.librorum.customViews.SectionsPagerAdapter
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity(),DBWrapper.DbInteraction{
    private lateinit var db: DBHandler


    override fun onDbLoaded() {
        Log.d("Librorum", "db loaded")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Librorum", "Created")
        doAsync {
            DBWrapper.registerCallback(this@MainActivity, "LoginActivity")
            DBWrapper.initDb(applicationContext, resources)
            db = DBWrapper.getInstance(this@MainActivity)
            Log.d("Librorum", "Successfully loaded db")
        }
        val buttonRecommend = findViewById<Button>(R.id.recombtn)
        buttonRecommend.setOnClickListener{
            val intent = Intent (this, Recommended::class.java)
            startActivity(intent)
        }
    }

}
