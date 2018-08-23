package com.dev.librorum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dev.librorum.data.DBWrapper
import java.util.*

fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) +  start

class Sort : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)
        val db = DBWrapper.getInstance(this)
        val usrDataList = db!!.listBooks("%")
        var id = (0..usrDataList.size).random()
        var book = db.findBook(id.toString())

    }
}
