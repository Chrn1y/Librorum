package com.dev.librorum.activities

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.utils.EXTRA_ID
import com.dev.librorum.data.DBWrapper
import com.squareup.picasso.Picasso
import android.content.Intent
import android.net.Uri
import android.widget.Button
import com.dev.librorum.R
import com.dev.librorum.utils.HEIGHT
import com.dev.librorum.utils.WIDTH
import com.dev.librorum.data.DBHandler

class BookInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)
        val db = DBWrapper.getInstance(this)
        val bookID = intent.getStringExtra(EXTRA_ID)
        val book =  db.listBooks(bookID)[0]
        val name = findViewById(R.id.nameInfo) as TextView
        name.text = book.name
        val author = findViewById(R.id.author) as TextView
        author.text = book.author
        val descr = findViewById(R.id.descr) as TextView
        descr.text = book.description
        val image = findViewById(R.id.imageInfo) as ImageView
        val buttonHome = findViewById(R.id.buttonInfo) as Button
        val buttonLike = findViewById(R.id.buttonLike) as Button
        Picasso.get()
                .load(book.picture)
                .resize((WIDTH /2.8).toInt(), (HEIGHT /2.8).toInt())
                .centerCrop()
                .into(image)


        if (book.like == "false")
            buttonLike.text = "Добавить в список желаемого"
        else
            buttonLike.text = "Убрать из списка желаемого"


        val values = ContentValues()
        buttonHome.setOnClickListener{
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(book.url))
            startActivity(browserIntent)
        }

            buttonLike.setOnClickListener {
                if (buttonLike.text == "Добавить в список желаемого") {

                    values.put(DBHandler.LIKE, "true")
                    db.updateBook(values, book._id)
                    buttonLike.text = "Убрать из списка желаемого"

                } else {

                    values.put(DBHandler.LIKE, "false")
                    db.updateBook(values, book._id)
                    buttonLike.text = "Добавить в список желаемого"

                }
            }

    }
}
