package com.dev.librorum

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import com.dev.librorum.Utils.EXTRA_ID
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
        val dataList = db.listLikes()



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted)

        val textSorted = findViewById<TextView>(R.id.textSorted)

        if (dataList.size == 0)
            textSorted.text = "Ваш список желаемого пуст"
        else
        textSorted.text = ""

        adapter = RecyclerSorted(this, dataList) {bookData ->

            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, bookData._id.toString())
            startActivity(intent)

        }
        SortedList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        SortedList.layoutManager = layoutManager
        SortedList.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        lateinit var adapter : RecyclerSorted
        val db = DBWrapper.getInstance(this)
        val dataList = db.listLikes()

        val textSorted = findViewById<TextView>(R.id.textSorted)
        if (dataList.size == 0)
            textSorted.text = "Ваш список желаемого пуст"
        else
            textSorted.text = ""
        adapter = RecyclerSorted(this, dataList) {bookData ->

            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, bookData._id.toString())
            startActivity(intent)

        }
        SortedList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        SortedList.layoutManager = layoutManager
        SortedList.setHasFixedSize(true)
    }
}
