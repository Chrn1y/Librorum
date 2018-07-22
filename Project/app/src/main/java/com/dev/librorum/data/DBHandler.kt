package com.dev.librorum.data

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import java.util.*

class DBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "BooksDB"
        const val DB_VERSION = 1
        const val TABLE_NAME = "BookTable"
        const val ID = "id"
        const val TITLE = "title"
        //const val COVER = "cover"
        const val GENRE = "genre"
        const val DESCRIPTION = "description"
        const val LIKE = "like"
    }

    private var sqlObj: SQLiteDatabase = this.writableDatabase // Сущность SQLiteDatabase

    override fun onCreate(currentDB: SQLiteDatabase?) { // Вызывается при генерации БД
        val sql1 = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $ID  INTEGER PRIMARY KEY, $TITLE TEXT, $GENRE TEXT, $DESCRIPTION TEXT, $LIKE TEXT );"
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
        val cols = arrayOf(ID, TITLE,  GENRE, DESCRIPTION, LIKE)
        val selectArgs = arrayOf(key)

        val cursor = sqlQB.query(sqlObj, cols, findIn + " like ?", selectArgs, null, null, TITLE)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(TITLE))
//                val cover = cursor.getString(cursor.getColumnIndex(COVER))
                val genre = cursor.getString(cursor.getColumnIndex(GENRE))
                val description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                val like = cursor.getString(cursor.getColumnIndex(LIKE))
                resultList.add(BookData(id, title, /*cover,*/ genre, description, like))

            } while (cursor.moveToNext())
        }
        return resultList

    }

    fun listBooks(key: String): ArrayList<BookData> {
        return loadListFromDB(key, GENRE)
    }

    fun listLikes(key: String): ArrayList<BookData> {
        return loadListFromDB(key, LIKE)
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

            val usrDataList = db!!.listBooks("%")
            val inputStream = resources.openRawResource(R.raw.test)
            val lines = BufferedReader(InputStreamReader(inputStream)).readLines().map {
                it.split(",")
            }

            if (usrDataList.size == 0 ) {
                for (temp in usrDataList) {
                    db!!.removeBook(temp._id)
                }

                val values = ContentValues()
                for (i in 0..(lines.size - 1)) {
                    values.put(DBHandler.TITLE, lines[i][0])
                    values.put(DBHandler.GENRE, lines[i][1])
                    values.put(DBHandler.DESCRIPTION, lines[i][2])

                    db!!.addBook(values)
                }
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
