package com.example.myapplication.apiinterface

import com.example.myapplication.dataclass.MovieItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiImageInterface {
    @GET("list")
    suspend fun getData(): Response<List<MovieItem>>
}