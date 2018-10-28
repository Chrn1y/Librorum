package com.dev.librorum.Utils

import android.content.Context

class Prefs(context: Context){

    val PREFERENCE_NAME="librorum"
    val PREFERENCE_FIRST = "first"
    val PREFERENCE_BOOKS = "bookNumber"
    val PREFERENCE_LOADING = "loading"
    val PREFERENCE_ALLBOOKS = "allBooks"
    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun firstTime() : Boolean{
        return preference.getBoolean(PREFERENCE_FIRST, true)
    }
    fun readLoading() : Boolean{
        return preference.getBoolean(PREFERENCE_LOADING, false)
    }
    fun wannaReadLoading(){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_LOADING, true)
        editor.apply()
    }
    fun finishedReadingLoading(){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_LOADING, false)
        editor.apply()
    }
    fun notFirstTime(){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_FIRST, false)
        editor.apply()
    }

    fun getBookNumber() : Int{
        return preference.getInt(PREFERENCE_BOOKS, 0)
    }

    fun setBookNumber(number : Int){
        val editor = preference.edit()
        editor.putInt(PREFERENCE_BOOKS, number)
        editor.apply()
    }

    fun allBooks() : String{
        return preference.getString(PREFERENCE_ALLBOOKS, "")
    }

    fun setBooks(books: String){

        val editor = preference.edit()
        editor.putString(PREFERENCE_ALLBOOKS, books)
        editor.apply()

    }
}