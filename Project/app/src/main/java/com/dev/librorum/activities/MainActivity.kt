package com.dev.librorum.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.dev.librorum.R
import com.dev.librorum.utils.Prefs
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            val buttonRecommend = findViewById<Button>(R.id.recombtn)
            val buttonSorted = findViewById<Button>(R.id.sortedbtn)
            val buttonStart = findViewById<Button>(R.id.sortbtn)

            buttonRecommend.setOnClickListener {
                try {
                    val intent = Intent(this@MainActivity, Recommended::class.java)
                    startActivity(intent)
                    buttonRecommend.setOnClickListener {

                    }
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }
            }

            buttonSorted.setOnClickListener {

                val intent = Intent(this@MainActivity, Sorted::class.java)
                startActivity(intent)

                buttonSorted.setOnClickListener {

                }
            }

            buttonStart.setOnClickListener {
                try {
                    val db = DBWrapper.getInstance(this)
                    val dbc = DBCWrapper.getInstance(this)

                    if (db.listBooks("%").size > 0 && dbc.listCategories("%").size > 0) {
                        val intent = Intent(this@MainActivity, Sort::class.java)
                        startActivity(intent)
                        buttonStart.setOnClickListener {
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Error", e.toString());
                }
            }

            val prefs = Prefs(this)
            val info = findViewById<ImageView>(R.id.info)
            info.setOnClickListener {
                prefs.wannaReadLoading()
                val intent = Intent(this@MainActivity, Loading::class.java)
                startActivity(intent)
            }

            doAsync {
                DBWrapper.initDb(applicationContext, resources)

            }
        }
        catch (e: Exception){

            Log.d("Librorum", e.toString())

            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        try {

            val buttonSorted = findViewById<Button>(R.id.sortedbtn)
            val buttonStart = findViewById<Button>(R.id.sortbtn)
            val buttonRecommend = findViewById<Button>(R.id.recombtn)

            buttonRecommend.setOnClickListener {
                try {
                    val intent = Intent(this@MainActivity, Recommended::class.java)
                    startActivity(intent)
                    buttonRecommend.setOnClickListener {

                    }
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }
            }

            buttonSorted.setOnClickListener {

                val intent = Intent(this@MainActivity, Sorted::class.java)
                startActivity(intent)

                buttonSorted.setOnClickListener {

                }
            }

            buttonStart.setOnClickListener {
                try {
                    val db = DBWrapper.getInstance(this)
                    val dbc = DBCWrapper.getInstance(this)

                    if (db.listBooks("%").size > 0 && dbc.listCategories("%").size > 0) {
                        val intent = Intent(this@MainActivity, Sort::class.java)
                        startActivity(intent)
                        buttonStart.setOnClickListener {
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }
            }
        }catch (e: Exception){

            Log.d("Librorum", e.toString())
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {

    }
}
