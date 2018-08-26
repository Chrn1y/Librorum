package com.dev.librorum.data

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.dev.librorum.R
import org.jetbrains.anko.runOnUiThread
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.collections.ArrayList

class DBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "BooksDB"
        const val DB_VERSION = 1
        const val TABLE_NAME = "BookTable"
        const val ID = "id"
        const val URL = "url"
        const val CATEGORY = "categoryId"
        const val PICTURE = "pic"
        const val AUTHOR = "auth"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val LIKE = "false"
    }

    private var sqlObj: SQLiteDatabase = this.writableDatabase // Сущность SQLiteDatabase

    override fun onCreate(currentDB: SQLiteDatabase?) { // Вызывается при генерации БД
        val sql1 = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $ID  INTEGER PRIMARY KEY, $URL TEXT, $CATEGORY TEXT, $PICTURE TEXT, $AUTHOR TEXT, $NAME TEXT,  $DESCRIPTION TEXT, $LIKE TEXT);"
        currentDB!!.execSQL(sql1)
    }

    override fun onUpgrade(currentDB: SQLiteDatabase?, prevDBVersion: Int, nextDBVersion: Int) {
        currentDB!!.execSQL("Drop table IF EXISTS $TABLE_NAME")
        onCreate(currentDB)
    }

    fun addBook(values: ContentValues) = sqlObj.insert(TABLE_NAME, "", values)

    fun removeBook(id: Int) = sqlObj.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))

    fun updateBook(values: ContentValues, id: Int) = sqlObj.update(TABLE_NAME, values, "id=?", arrayOf(id.toString()))

    private fun loadListFromDB(key: String, findIn: String? = null): ArrayList<BookData> {
        val resultList = ArrayList<BookData>()
        if (findIn == null) {
            return resultList
        }

        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = TABLE_NAME
        val cols = arrayOf(ID, URL, CATEGORY, PICTURE, AUTHOR, NAME, DESCRIPTION, LIKE)
        val selectArgs = arrayOf(key)

        val cursor = sqlQB.query(sqlObj, cols, findIn + " like ?", selectArgs, null, null, NAME)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val url = cursor.getString(cursor.getColumnIndex(URL))
                val category = cursor.getString(cursor.getColumnIndex(CATEGORY))
                val picture = cursor.getString(cursor.getColumnIndex(PICTURE))
                val author = cursor.getString(cursor.getColumnIndex(AUTHOR))
                val name = cursor.getString(cursor.getColumnIndex(NAME))
                val description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                val like = cursor.getString(cursor.getColumnIndex(LIKE))
                resultList.add(BookData(id, url, category, picture, author, name,  description, like ))

            } while (cursor.moveToNext())
        }
        cursor.close()
        return resultList

    }

//    fun listCategories(): List<String>{
//    val list = loadListFromDB("%", ID)
//        val unique = list.distinctBy{it.categoryId}
//        Log.d("Librorum", unique.size.toString())
//        val cat = ArrayList<String>()
//        for(i in 0..(unique.size-1)){
//            cat.add(unique[i].categoryId)
//        }
//        Log.d("Librorum", cat.size.toString())
//        return cat
//
//    }

    fun listBooks(key: String): ArrayList<BookData> {
        return loadListFromDB(key, ID)
    }

    fun listLikes(): ArrayList<BookData> {
        return loadListFromDB("true", LIKE)
    }

    fun findBook (key: String): BookData {
        return loadListFromDB(key, ID)[0]
    }


}

class DBWrapper private constructor() {
    companion object {
        private var listeners: MutableMap<String, DbInteraction> = mutableMapOf()
        private var db: DBHandler? = null

        @JvmStatic
        fun getInstance(ctx: Context): DBHandler {
            if (db == null)
                db = DBHandler(ctx)

            return db!!
        }

        @JvmStatic
        fun registerCallback(ctx: Context, key: String) {
            listeners[key] = ctx as DbInteraction

        }


        @JvmStatic
        fun initDb(ctx: Context, resources: Resources) {
            getInstance(ctx)

            fun getByLines (lines: List<List<String>>) : Unit{
                val values = ContentValues()
                for (i in 1..(lines.size - 1)) {
                    values.put(DBHandler.URL, lines[i][0])
                    values.put(DBHandler.CATEGORY, lines[i][1])
                    values.put(DBHandler.PICTURE, lines[i][2])
                    values.put(DBHandler.AUTHOR, lines[i][3])
                    values.put(DBHandler.NAME, lines[i][4])
                    values.put(DBHandler.DESCRIPTION, lines[i][5])
                    values.put(DBHandler.LIKE, "false")
                    db!!.addBook(values)
                }
            }
            val dataList = db!!.listBooks("%")


            if (dataList.size == 0 ) {
                var inputStream = resources.openRawResource(R.raw.first)
                var line = BufferedReader(InputStreamReader(inputStream)).readLines().map {
                    it.split("|")
                }
//                getByLines(line)
//                inputStream = resources.openRawResource(R.raw.second)
//                line = BufferedReader(InputStreamReader(inputStream)).readLines().map {
//                    it.split("|")
//                }
//                getByLines(line)
//                inputStream = resources.openRawResource(R.raw.third)
//                line = BufferedReader(InputStreamReader(inputStream)).readLines().map {
//                    it.split("|")
//                }
//                getByLines(line)
//                inputStream = resources.openRawResource(R.raw.fourth)
//                line = BufferedReader(InputStreamReader(inputStream)).readLines().map {
//                    it.split("|")
//                }
//                Log.d("Librorum", line.size.toString())
                getByLines(line)

            }
            ctx.runOnUiThread {
                listeners.forEach { it.value.onDbLoaded() }
            }

        }
    }

    interface DbInteraction {
        fun onDbLoaded()
    }
}
