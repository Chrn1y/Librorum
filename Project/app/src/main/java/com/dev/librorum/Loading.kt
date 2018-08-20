package com.dev.librorum

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync
import java.util.*

class Loading : AppCompatActivity()/*,DBWrapper.DbInteraction*/ {
    //val db = DBWrapper.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@Loading, MainActivity::class.java))
                finish()


            }

        }, 800)
//        doAsync {
//            DBWrapper.initDb(applicationContext, resources)
//            db = DBWrapper.getInstance(this@Loading)
//            Log.d("Librorum", "Successfully loaded db")
//
//        }
    }




}
