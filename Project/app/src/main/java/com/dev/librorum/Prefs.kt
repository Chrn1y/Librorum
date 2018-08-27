package com.dev.librorum

import android.content.Context

class Prefs(context: Context){

    val PREFERENCE_NAME="librorum"
    val PREFERENCE_FIRST = "first"

    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun firstTime() : Boolean{
        return preference.getBoolean(PREFERENCE_FIRST, true)
    }

    fun notFirstTime(){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_FIRST, false)
        editor.apply()
    }
}