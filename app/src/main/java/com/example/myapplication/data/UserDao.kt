package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotes(userData: UserData)
    @Query("SELECT*FROM user_table ORDER BY id ASC")
    fun readAllData(): List<UserData>

    @Query("DELETE FROM user_table ")
    fun deleteALl()

}