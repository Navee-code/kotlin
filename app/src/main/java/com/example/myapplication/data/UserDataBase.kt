package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao():UserDao

    companion object{
        @Volatile
        private var Instance:UserDataBase?=null
        fun getDatabase(context:Context):UserDataBase{
            val tempInstance= Instance
            if(tempInstance!=null){
               return tempInstance
            }
            synchronized(this){
          val instance= Room.databaseBuilder(
          context.applicationContext,UserDataBase::class.java, "user_database").build()
       Instance=instance
         return instance
            }



        }
 }
}