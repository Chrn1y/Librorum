package com.dev.librorum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.Utils.EXTRA_ID
import com.dev.librorum.data.DBWrapper
import com.squareup.picasso.Picasso
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import org.jetbrains.anko.toast


class BookInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)
        val db = DBWrapper.getInstance(this)
        val bookID = intent.getStringExtra(EXTRA_ID)
        val book =  db!!.listBooks(bookID)[0]

        val name = findViewById(R.id.nameInfo) as TextView
        name.text = book.name

        val author = findViewById(R.id.author) as TextView
        author.text = book.author

        val descr = findViewById(R.id.descr) as TextView
        descr.text = book.description

        val image = findViewById(R.id.imageInfo) as ImageView

        Picasso.get()
                .load(book.picture)
                .resize(580, 800)
                //.fit()
                .centerCrop()
                .into(image)

        val mIdButtonHome = findViewById(R.id.buttonInfo) as Button

        mIdButtonHome.setOnClickListener(){
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(book.url))
            startActivity(browserIntent)
        }
    }
}
