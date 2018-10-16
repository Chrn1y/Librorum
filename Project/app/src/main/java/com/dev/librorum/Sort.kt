
package com.dev.librorum

import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.Utils.EXTRA_ID
import com.dev.librorum.data.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sort.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.util.*
import kotlin.math.absoluteValue

class Sort : AppCompatActivity() {


    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start
    var index = 0

    val db = DBWrapper.getInstance(this)
    val dbc = DBCWrapper.getInstance(this)
//    val dataList = db.listBooks("%")
    var categoriesList = dbc.listCategories("%")
    fun getBook():BookData{

        if (index+1 == categoriesList.size)
            index = 0
        else
            index++

        return db.getRandomCatBook(dbc, index)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)
        val db = DBWrapper.getInstance(this)
        val dbc = DBCWrapper.getInstance(this)
        val dataList = db.listBooks("%")
        var categoriesList = dbc.listCategories("%")

        val number = dataList.size
        var id = (0..(number-1)).random().toString()
        var book =  getBook()
        val values = ContentValues()
        val name = findViewById(R.id.nameSort) as TextView
        name.text = book.name

//        toast(catDataList.size.toString())
        val image = findViewById(R.id.imageSort) as ImageView
        Picasso.get()
                .load(book.picture)
                .resize(430, 640)
                //.fit()
                .centerCrop()
                .into(image)

        name.setOnClickListener {
            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }

        image.setOnClickListener {
            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }

        var sum  = 0.0
        val buttonLike = findViewById<Button>(R.id.likebtn)
        buttonLike.setOnClickListener{
//            algorithm
            categoriesList = dbc.listCategories("%")
            categoriesList.forEach {
                sum += it.number.toDouble().absoluteValue
            }
            val values = ContentValues()
            values.put(DBCHandler.NUMBER, ((sum/2)+1).toString())
            dbc.updateCategory(values,dbc.categoryId(book.categoryId))

            id = (0..number).random().toString()
            book =  getBook()
            Picasso.get()
                    .load(book.picture)
                    .resize(580, 800)
                    //.fit()
                    .centerCrop()
                    .into(image)
            name.text = book.name

        }

        val buttonDislike = findViewById<Button>(R.id.dislikebtn)
        buttonDislike.setOnClickListener{
            categoriesList = dbc.listCategories("%")
            categoriesList.forEach {
                sum += it.number.toDouble().absoluteValue
            }
            val values = ContentValues()
            values.put(DBCHandler.NUMBER, (-(sum/2)-1).toString())
            dbc.updateCategory(values,dbc.categoryId(book.categoryId))

            id = (0..number).random().toString()
            book =  getBook()
            Picasso.get()
                    .load(book.picture)
                    .resize(580, 800)
                    //.fit()
                    .centerCrop()
                    .into(image)
            name.text = book.name

        }

        arrow.setOnClickListener{

            id = (0..number).random().toString()
            book =  getBook()
            Picasso.get()
                    .load(book.picture)
                    .resize(580, 800)
                    //.fit()
                    .centerCrop()
                    .into(image)
            name.text = book.name
        }

        val buttonAdd = findViewById<Button>(R.id.addbtn)
        if (book.like == "false")
            buttonAdd.text = "Добавить в список желаемого"
        else
            buttonAdd.text = "Убрать из списка желаемого"

        buttonAdd.setOnClickListener() {
            if (buttonAdd.text == "Добавить в список желаемого") {

                values.put(DBHandler.LIKE, "true")
                db.updateBook(values, book._id)
                buttonAdd.text = "Убрать из списка желаемого"
            } else {

                values.put(DBHandler.LIKE, "false")
                db.updateBook(values, book._id)
                buttonAdd.text = "Добавить в список желаемого"
            }
        }

//        doAsync {
//            DBWrapper.initDb(applicationContext, resources)
//        }
    }
}