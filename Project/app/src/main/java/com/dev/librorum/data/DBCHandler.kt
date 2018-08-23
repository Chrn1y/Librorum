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

class DBCHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "CategoriesDB"
        const val DB_VERSION = 1
        const val TABLE_NAME = "CategoryTable"
        const val ID = "id"
        const val TYPE = "type"
        const val NUMBER = "0"
    }

    private var sqlObj: SQLiteDatabase = this.writableDatabase // Сущность SQLiteDatabase

    override fun onCreate(currentDB: SQLiteDatabase?) { // Вызывается при генерации БД
        val sql1 = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $ID  INTEGER PRIMARY KEY, $TYPE TEXT, $NUMBER TEXT);"
        currentDB!!.execSQL(sql1)
    }

    override fun onUpgrade(currentDB: SQLiteDatabase?, prevDBVersion: Int, nextDBVersion: Int) {
        currentDB!!.execSQL("Drop table IF EXISTS $TABLE_NAME")
        onCreate(currentDB)
    }

    fun addCategory(values: ContentValues) = sqlObj.insert(TABLE_NAME, "", values)

    fun removeCategory(id: Int) = sqlObj.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))

    fun updateCategory(values: ContentValues, id: Int) = sqlObj.update(TABLE_NAME, values, "id=?", arrayOf(id.toString()))

    private fun loadListFromDB(key: String, findIn: String? = null): ArrayList<CategoryData> {
        val resultList = ArrayList<CategoryData>()
        if (findIn == null) {
            return resultList
        }

        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = TABLE_NAME
        val cols = arrayOf(ID, TYPE, NUMBER)
        val selectArgs = arrayOf(key)

        val cursor = sqlQB.query(sqlObj, cols, findIn + " like ?", selectArgs, null, null, TYPE)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val type = cursor.getString(cursor.getColumnIndex(TYPE))
                val number = cursor.getString(cursor.getColumnIndex(NUMBER))
                resultList.add(CategoryData(id, type, number))

            } while (cursor.moveToNext())
        }
        cursor.close()
        return resultList

    }

    fun listCategories(key: String): ArrayList<CategoryData> {
        return loadListFromDB(key, ID)
    }

    fun findCategory (key: String): CategoryData {
        return loadListFromDB(key, ID)[0]
    }


}

class DBCWrapper private constructor() {
    companion object {
//        private var listeners: MutableMap<String, DbInteraction> = mutableMapOf()
        private var db: DBCHandler? = null

        @JvmStatic
        fun getInstance(ctx: Context): DBCHandler {
            if (db == null)
                db = DBCHandler(ctx)

            return db!!
        }

//        @JvmStatic
//        fun registerCallback(ctx: Context, key: String) {
//            listeners[key] = ctx as DbInteraction
//
//        }


        @JvmStatic
        fun initDb(ctx: Context, resources: Resources) {
            getInstance(ctx)

            val usrDataList = db!!.listCategories("%")
            val inputStream = resources.openRawResource(R.raw.fant)
            val lines = BufferedReader(InputStreamReader(inputStream)).readLines().map {
                it.split("|")
            }

            if (usrDataList.size == 0 ) {
                for (temp in usrDataList) {
                    db!!.removeCategory(temp._id)
                }

                val values = ContentValues()
//                fill in the base
            }
//            ctx.runOnUiThread {
//                listeners.forEach { it.value.onDbLoaded() }
//            }
        }
    }

//    interface DbInteraction {
//        fun onDbLoaded()
//    }
}
