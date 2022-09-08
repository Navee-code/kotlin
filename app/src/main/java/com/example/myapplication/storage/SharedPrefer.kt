package com.example.myapplication.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPrefer {
    fun sharedSetBoolean(b: Boolean,context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putBoolean("taken",b)
        editor.apply()
        editor.commit()
    }
    fun sharedGetBoolean(context: Context):Boolean{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val activity = sharedPreferences.getBoolean("taken",false)
        return activity
    }

//    fun sharedSetString(b: String, context: Context) {
//        val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
//        editor.putString("taken",b)
//        editor.apply()
//        editor.commit()
//    }
//    fun sharedGetString(context: Context): String? {
//        val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
//        val activity = sharedPreferences.getString("taken","welcome")
//       return activity
//    }

}