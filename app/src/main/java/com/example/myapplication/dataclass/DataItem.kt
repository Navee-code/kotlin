package com.example.myapplication.dataclass

import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title_my: String,
    @SerializedName("userId")
    val userId: Int

)