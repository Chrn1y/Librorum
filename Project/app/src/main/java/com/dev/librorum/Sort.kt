
package com.dev.librorum

import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.Utils.EXTRA_ID
import com.dev.librorum.data.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.activity_sort.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.util.*
import kotlin.math.absoluteValue
import android.R.attr.y
import android.R.attr.x
import android.graphics.Point
import android.view.Display



class Sort : AppCompatActivity() {


    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start
    var index = 0

    val db = DBWrapper.getInstance(this)
    val dbc = DBCWrapper.getInstance(this)
//    val dataList = db.listBooks("%")
    var categoriesList = dbc.listCategories("%")
    fun getBook():BookData{

        if (index+1 == categoriesList.size) {
            index = 0
            categoriesList.shuffle()
        }
        else
            index++
        Log.d("Librorum", index.toString())
        return db.getRandomCatBook(dbc, index)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)
        val db = DBWrapper.getInstance(this)
        val dbc = DBCWrapper.getInstance(this)
        val dataList = db.listBooks("%")
        var categoriesList = dbc.listCategories("%")
        categoriesList.shuffle()
        val buttonAdd = findViewById<Button>(R.id.addbtn)
        val image = findViewById(R.id.imageSort) as ImageView
        val number = dataList.size
        var id = (0..(number-1)).random().toString()
        fun fitPicBook(book:BookData){
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y
            Picasso.get()
                    .load(book.picture)
//                .resize(430, 640)
//                .fit()
//                .transform(Transformation)
                    .resize((width/2.8).toInt(), height/3)
                    .centerCrop()
                    .into(image)
        }
        var book =  getBook()
        fitPicBook(book)
        val values = ContentValues()
        val name = findViewById(R.id.nameSort) as TextView
        val author = findViewById(R.id.authorSort) as TextView
        name.text = book.name
        author.text = book.author
//        toast(catDataList.size.toString())



        name.setOnClickListener {
            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }
    author.setOnClickListener {
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
            fitPicBook(book)
            name.text = book.name
            author.text = book.author
            if (book.like == "false")
                buttonAdd.text = "Добавить в список желаемого"
            else
                buttonAdd.text = "Убрать из списка желаемого"
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
            fitPicBook(book)
            name.text = book.name
            author.text = book.author
            if (book.like == "false")
                buttonAdd.text = "Добавить в список желаемого"
            else
                buttonAdd.text = "Убрать из списка желаемого"
        }

        arrow.setOnClickListener{

            id = (0..number).random().toString()
            book =  getBook()
            fitPicBook(book)
            name.text = book.name
            author.text = book.author
            if (book.like == "false")
                buttonAdd.text = "Добавить в список желаемого"
            else
                buttonAdd.text = "Убрать из списка желаемого"
        }

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