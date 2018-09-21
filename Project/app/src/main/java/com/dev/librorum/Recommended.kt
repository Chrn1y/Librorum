package com.dev.librorum

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dev.librorum.Utils.EXTRA_ID
import com.dev.librorum.customViews.RecyclerRecommended
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import kotlinx.android.synthetic.main.activity_recommended.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class Recommended : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var adapter: RecyclerRecommended
        val db = DBWrapper.getInstance(this)
        val dbc = DBCWrapper.getInstance(this)
        val dataList = db.getLargestCat(dbc)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended)
        toast(db.listBooks("%").size.toString())
        adapter = RecyclerRecommended(this, dataList) { bookData ->

            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, bookData._id.toString())
            startActivity(intent)

        }
        RecommendedList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        RecommendedList.layoutManager = layoutManager
        RecommendedList.setHasFixedSize(true)

        doAsync {
            DBWrapper.initDb(applicationContext, resources)
        }
    }
}
