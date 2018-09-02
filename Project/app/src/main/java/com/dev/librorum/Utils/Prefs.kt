package com.dev.librorum.Utils

import android.content.Context

class Prefs(context: Context){

    val PREFERENCE_NAME="librorum"
    val PREFERENCE_FIRST = "first"
    val PREFERENCE_BOOKS = "bookNumber"
    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun firstTime() : Boolean{
        return preference.getBoolean(PREFERENCE_FIRST, true)
    }

    fun notFirstTime(){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_FIRST, false)
        editor.apply()
    }

    fun bookNumber() : Int{
        return preference.getInt(PREFERENCE_BOOKS, 0)
    }

    fun setBookNumber(number : Int){
        val editor = preference.edit()
        editor.putInt(PREFERENCE_BOOKS, number)
        editor.apply()
    }

}