package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotes(userData: UserData)
    @Query("SELECT*FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserData>>

}