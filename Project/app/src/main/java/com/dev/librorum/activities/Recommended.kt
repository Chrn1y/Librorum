package com.dev.librorum.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.dev.librorum.R
import com.dev.librorum.utils.EXTRA_ID
import com.dev.librorum.customViews.RecyclerRecommended
import com.dev.librorum.customViews.SimpleDividerItemDecoration
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBWrapper

class Recommended : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
        lateinit var adapter: RecyclerRecommended
        val db = DBWrapper.getInstance(this)
        val dbc = DBCWrapper.getInstance(this)
        val dataList = db.getLargestCat(dbc)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended)

            val textRecom = findViewById<TextView>(R.id.textRecom)
            textRecom.text = ""

            if (dataList.size == 0)
                textRecom.text = "Ваш список рекомендаций пока пуст"
            else
                textRecom.text = ""

            adapter = RecyclerRecommended(this, dataList) { bookData ->

                val intent = Intent(this@Recommended, BookInfo::class.java)
                intent.putExtra(EXTRA_ID, bookData._id.toString())
                startActivity(intent)

            }
            val recommendedList = findViewById<RecyclerView>(R.id.recommendedList)
            recommendedList.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            recommendedList.layoutManager = layoutManager
            recommendedList.setHasFixedSize(true)
            recommendedList.addItemDecoration(SimpleDividerItemDecoration(
                    getApplicationContext()
            ))
        } catch (e : Exception){

            Log.d("Librorum", e.toString())
            startActivity(Intent(this@Recommended, Recommended::class.java))

        }
    }
}
