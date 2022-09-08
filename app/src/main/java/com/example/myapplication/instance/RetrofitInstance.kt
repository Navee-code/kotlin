package com.example.myapplication.instance

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitInstance {
      val URL = "https://jsonplaceholder.typicode.com/"
      fun getInstance(): Retrofit {
            return Retrofit.Builder()
                  .addConverterFactory(GsonConverterFactory.create())
                  .baseUrl(URL)
                  .build()
      }
}