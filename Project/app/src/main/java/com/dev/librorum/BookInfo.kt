package com.dev.librorum

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.Utils.EXTRA_ID
import com.dev.librorum.data.DBWrapper
import com.squareup.picasso.Picasso
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.View
import android.widget.Button
import com.dev.librorum.Utils.HEIGHT
import com.dev.librorum.Utils.WIDTH
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBHandler
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast


class BookInfo : AppCompatActivity() {

    fun checkAndChange() : Unit{

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
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

        Picasso.get()
                .load(book.picture)
                .resize((WIDTH /2.8).toInt(), (HEIGHT /2.8).toInt())
                .centerCrop()
                .into(image)

        val ButtonHome = findViewById(R.id.buttonInfo) as Button
        val ButtonLike = findViewById(R.id.buttonLike) as Button
        if (book.like == "false")
            ButtonLike.text = "Добавить в список желаемого"
        else
            ButtonLike.text = "Убрать из списка желаемого"


        val values = ContentValues()
        ButtonHome.setOnClickListener(){
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(book.url))
            startActivity(browserIntent)
        }

            ButtonLike.setOnClickListener() {
                if (ButtonLike.text == "Добавить в список желаемого") {

                    values.put(DBHandler.LIKE, "true")
                    db.updateBook(values, book._id)
                    ButtonLike.text = "Убрать из списка желаемого"
                } else {

                    values.put(DBHandler.LIKE, "false")
                    db.updateBook(values, book._id)
                    ButtonLike.text = "Добавить в список желаемого"
                }
            }

    }
}
