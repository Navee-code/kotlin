package com.example.myapplication.apiinterface

import com.example.myapplication.dataclass.DataItem
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {
    @GET("posts")
  suspend fun getData(): Response<List<DataItem>>
    @DELETE("posts/{body}")
    suspend fun deleteData(@Path("body")body: String)
}