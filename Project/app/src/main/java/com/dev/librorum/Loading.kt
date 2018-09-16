package com.dev.librorum

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync
import java.io.BufferedReader
import java.io.InputStreamReader
import com.dev.librorum.Utils.Prefs
import org.jetbrains.anko.toast


class Loading : AppCompatActivity(), DBWrapper.DbInteraction {
    private var flag: Boolean = false

    private fun changename (text: String){
        val name = findViewById(R.id.loadingText) as TextView
        for (i in 0..text.length-1){
                name.postDelayed(Runnable { name.text = name.text.toString()+text[i]

                }, 0)
        }
        name.text = ""
    }


    override fun onDbLoaded() {
        flag = true
        toast("Все готово")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading)
        val prefs = Prefs(this)


        val inputStream = resources.openRawResource(R.raw.loading)
        val line = BufferedReader(InputStreamReader(inputStream)).readLines()
        var number = 1
        val tap = findViewById<ConstraintLayout>(R.id.nextText)


        if(prefs.firstTime() != true){
            startActivity(Intent(this@Loading, MainActivity::class.java))
        }
        doAsync {
            DBWrapper.registerCallback(this@Loading, "Loading")
            DBWrapper.initDb(applicationContext, resources)

            if (number >= line.size) {
                startActivity(Intent(this@Loading, MainActivity::class.java))

                prefs.notFirstTime()
            }
        }


        tap.setOnClickListener {
            if(number < line.size) {
                changename(line[number])
                number++
            }
            else if (flag == true) {

                startActivity(Intent(this@Loading, MainActivity::class.java))

                prefs.notFirstTime()
            }
        }


        changename(line[number-1])


    }


}
