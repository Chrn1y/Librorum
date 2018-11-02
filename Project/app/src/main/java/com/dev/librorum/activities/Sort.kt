
package com.dev.librorum.activities

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.R
import com.dev.librorum.utils.EXTRA_ID
import com.dev.librorum.data.*
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.absoluteValue
import com.dev.librorum.utils.HEIGHT
import com.dev.librorum.utils.WIDTH


class Sort : AppCompatActivity() {


    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start
    var index = 0
    var like = true
    val db = DBWrapper.getInstance(this)
    val dbc = DBCWrapper.getInstance(this)
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
        val image = findViewById(R.id.imageSort) as ImageView
        val number = dataList.size
        var id = (0..(number-1)).random().toString()
        val arrow = findViewById<ImageView>(R.id.arrow)

        fun fitPicBook(book:BookData){
            Picasso.get()
                    .load(book.picture)
                    .resize((WIDTH /2.8).toInt(), (HEIGHT /2.8).toInt())
                    .centerCrop()
                    .into(image)
        }

        var book =  getBook()
        fitPicBook(book)
        val name = findViewById(R.id.nameSort) as TextView
        val author = findViewById(R.id.authorSort) as TextView
        name.text = book.name
        author.text = book.author

        name.setOnClickListener {
            val intent = Intent(this@Sort, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }

        author.setOnClickListener {
            val intent = Intent(this@Sort, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }

        image.setOnClickListener {
            val intent = Intent(this@Sort, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }

        var sum  = 0.0
        val buttonLike = findViewById<Button>(R.id.likebtn)
        buttonLike.setOnClickListener{

            categoriesList = dbc.listCategories("%")
            categoriesList.forEach {
                sum += it.number.toDouble().absoluteValue
            }

            val values = ContentValues()
            values.put(DBCHandler.NUMBER, ((sum/2)+1).toString())
            dbc.updateCategory(values,dbc.categoryId(book.categoryId))
            if (like == false) {

                val values = ContentValues()
                values.put(DBHandler.LIKE, "true")
                db.updateBook(values, book._id)
                like = true

            } else {

                val values = ContentValues()
                values.put(DBHandler.LIKE, "false")
                db.updateBook(values, book._id)
                like = false

            }

            id = (0..number).random().toString()
            book =  getBook()
            fitPicBook(book)
            name.text = book.name
            author.text = book.author

            if (book.like == "false")
                like = false
            else
                like = true

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
                like = false
            else
                like = true

        }
        arrow.setOnClickListener{

            id = (0..number).random().toString()
            book =  getBook()
            fitPicBook(book)
            name.text = book.name
            author.text = book.author

            if (book.like == "false")
                like = false
            else
                like = true
        }

        if (book.like == "false")
            like = false
        else
            like = true

    }
}