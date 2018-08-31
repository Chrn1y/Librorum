package com.dev.librorum

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.concurrent.schedule
import android.view.MotionEvent
import android.view.View.OnTouchListener



class Loading : AppCompatActivity(), DBWrapper.DbInteraction {
    //val db = DBWrapper.getInstance(this)
    private lateinit var db: DBHandler
    private var flag: Boolean = false
    private var type: Boolean = false
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable


    private fun changename (text: String){
        val name = findViewById(R.id.loadingText) as TextView
        for (i in 0..text.length-1){
                name.postDelayed(Runnable { name.text = name.text.toString()+text[i]

                }, 0)
        }
        name.text = ""
    }


    override fun onDbLoaded() {
        flag = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val prefs = Prefs(this)


        val inputStream = resources.openRawResource(R.raw.loading)
        val line = BufferedReader(InputStreamReader(inputStream)).readLines()
        var number = 1
        val tap = findViewById<ConstraintLayout>(R.id.nextText)


        if(prefs.firstTime() != true){
            number = line.size
        }
        doAsync {
            DBWrapper.registerCallback(this@Loading, "Loading")
            DBWrapper.initDb(applicationContext, resources)
//            db = DBWrapper.getInstance(this@Loading)


            if (number >= line.size) {

                startActivity(Intent(this@Loading, MainActivity::class.java))

            }
        }



        tap.setOnClickListener {
            if(number < line.size) {
                changename(line[number])
                number++
            }
            else if (flag == true) {

                startActivity(Intent(this@Loading, MainActivity::class.java))
            }
        }


        changename(line[number-1])
        prefs.notFirstTime()


    }



}
