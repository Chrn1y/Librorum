package com.dev.librorum

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.View
import android.widget.Button
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main)

        val buttonRecommend = findViewById<Button>(R.id.recombtn)
        buttonRecommend.setOnClickListener{

                val intent = Intent(this, Recommended::class.java)
                startActivity(intent)

        }

        val buttonSorted = findViewById<Button>(R.id.sortedbtn)
        buttonSorted.setOnClickListener{

            val intent = Intent(this, Sorted::class.java)
            startActivity(intent)

        }

        val buttonStart = findViewById<Button>(R.id.sortbtn)
        buttonStart.setOnClickListener{

            val intent = Intent(this, Sort::class.java)
            startActivity(intent)

        }
    }

    override fun onBackPressed() {

    }
}
