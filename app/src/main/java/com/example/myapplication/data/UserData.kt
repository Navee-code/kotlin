package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_table")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id:Int?,
  @ColumnInfo(name = "NOTES") val note:String?

)


