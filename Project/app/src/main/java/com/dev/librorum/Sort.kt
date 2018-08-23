
package com.dev.librorum

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.Utils.EXTRA_ID
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import com.squareup.picasso.Picasso
import org.jetbrains.anko.toast
import java.util.*

class Sort : AppCompatActivity() {

    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)
        val db = DBWrapper.getInstance(this)
        val usrDataList = db!!.listBooks("%")
        val number = usrDataList.size
        var id = (0..number).random().toString()
        var book =  db!!.findBook(id)
        val values = ContentValues()
        val name = findViewById(R.id.nameSort) as TextView
        name.text = book.name

        val image = findViewById(R.id.imageSort) as ImageView
        Picasso.get()
                .load(book.picture)
                .resize(580, 800)
                //.fit()
                .centerCrop()
                .into(image)

        image.setOnClickListener {
            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra(EXTRA_ID, book._id.toString())
            startActivity(intent)
        }


        val buttonLike = findViewById<Button>(R.id.likebtn)
        buttonLike.setOnClickListener{
//            algorithm

        }

        val buttonDislike = findViewById<Button>(R.id.dislikebtn)
        buttonDislike.setOnClickListener{
//            algorithm

        }

        val buttonAdd = findViewById<Button>(R.id.addbtn)
        buttonAdd.setOnClickListener{
            toast("Добавлено")
            values.put(DBHandler.LIKE, "true")
            db.updateBook(values, book._id)

            id = (0..number).random().toString()
            book =  db!!.findBook(id)
            Picasso.get()
                    .load(book.picture)
                    .resize(580, 800)
                    //.fit()
                    .centerCrop()
                    .into(image)
            name.text = book.name
        }


    }
}