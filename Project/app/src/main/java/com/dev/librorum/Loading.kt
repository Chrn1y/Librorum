package com.dev.librorum

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.Button
import android.widget.TextView
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync
import java.io.BufferedReader
import java.io.InputStreamReader
import com.dev.librorum.Utils.Prefs
import com.dev.librorum.data.DBCWrapper
import com.github.florent37.kotlin.pleaseanimate.please
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.toast


class Loading : AppCompatActivity(), DBWrapper.DbInteraction {
    private var flag: Boolean = false

    private fun changename (text: String){
        val name = findViewById(R.id.loadingText) as TextView
                name.text = text
    }


//    override fun onDbLoaded() {
//
//    }

    override fun onFileReaded() {
        flag = true
//        toast("Все готово")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val prefs = Prefs(this)

        val inputStream = resources.openRawResource(R.raw.loading)
        var line = BufferedReader(InputStreamReader(inputStream)).readLines()
        var number = 1
        val tap = findViewById<ConstraintLayout>(R.id.nextText)

        if(prefs.readLoading()) {
            line = line.dropLast(1)
//            toast("welldone")
        }

        if(prefs.firstTime() != true && prefs.readLoading() != true){
            startActivity(Intent(this@Loading, MainActivity::class.java))
            prefs.finishedReadingLoading()
        }
        if(prefs.firstTime() == true){
//            toast("YOUFUCKEDUPAGAIN")
            doAsync {
                DBCWrapper.initDb(applicationContext, resources)
                DBWrapper.registerCallback(this@Loading, "Loading")
                DBWrapper.readFile(applicationContext, resources)

                if (number >= line.size) {
                    startActivity(Intent(this@Loading, MainActivity::class.java))

                    prefs.finishedReadingLoading()
                    prefs.notFirstTime()
                }
//            DBWrapper.initDb(applicationContext, resources)
            }
        }

        tap.setOnClickListener {
                if(number < line.size) {
                    changename(line[number])
                    number++
                }
                else if (flag == true || prefs.readLoading() == true) {

                    startActivity(Intent(this@Loading, MainActivity::class.java))

                    prefs.finishedReadingLoading()
                    prefs.notFirstTime()
                }
        }

        val skip = findViewById<Button>(R.id.buttonSkip)
        skip.setOnClickListener(){
            number = line.size-1
            changename(line[number])
        }
        changename(line[number-1])


    }


}
