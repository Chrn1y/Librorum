package com.dev.librorum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dev.librorum.customViews.RecyclerRecommended
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import kotlinx.android.synthetic.main.activity_recommended.*
import org.jetbrains.anko.doAsync

class Recommended : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var adapter : RecyclerRecommended
        val db = DBWrapper.getInstance(this)
//        val db: DBHandler? = DBHandler(this)
        val usrDataList = db!!.listBooks("%")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended)

        adapter = RecyclerRecommended(this, usrDataList)
        RecommendedList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        RecommendedList.layoutManager = layoutManager
        RecommendedList.setHasFixedSize(true)

    }
}
