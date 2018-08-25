package com.dev.librorum

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.util.*

class Loading : AppCompatActivity(), DBWrapper.DbInteraction {
    //val db = DBWrapper.getInstance(this)
    private lateinit var db: DBHandler

    override fun onDbLoaded() {
        startActivity(Intent(this@Loading, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

//        DBWrapper.initDb(applicationContext, resources)
//        DBCWrapper.initDb(applicationContext, resources)
//        val dbc = DBCWrapper.getInstance(this)
//        val listCategories = dbc.listCategories("%")
//        val number = listCategories.size
//        toast(number.toString())
//        Timer().schedule(object : TimerTask() {
//            override fun run() {
//                startActivity(Intent(this@Loading, MainActivity::class.java))
//                finish()
//
//
//            }
//
//        }, 1000)
        doAsync {
        DBWrapper.registerCallback(this@Loading, "Loading")
        DBWrapper.initDb(applicationContext, resources)
        db = DBWrapper.getInstance(this@Loading)
        Log.d("db", "Successfully loaded db")
    }
    }

}
