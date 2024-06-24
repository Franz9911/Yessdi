package com.example.labretrofitktejemplo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseurl ="http:192.168.1.7:3000/"
    fun getInstance():Retrofit{
        return Retrofit.Builder().baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}