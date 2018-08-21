package com.dev.librorum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dev.librorum.customViews.RecyclerRecommended
import com.dev.librorum.customViews.RecyclerSorted
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import kotlinx.android.synthetic.main.activity_recommended.*
import kotlinx.android.synthetic.main.activity_sorted.*
import org.jetbrains.anko.doAsync

class Sorted : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var adapter : RecyclerSorted

        val db = DBWrapper.getInstance(this)
        val usrDataList = db!!.listBooks("%")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorted)


        adapter = RecyclerSorted(this, usrDataList)
        SortedList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        SortedList.layoutManager = layoutManager
        SortedList.setHasFixedSize(true)

    }
}
