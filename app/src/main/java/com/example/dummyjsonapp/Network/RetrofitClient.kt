package com.example.dummyjsonapp.Network

import com.example.dummyjsonapp.Interface.ApiInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val myRetrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    val apiInterface: ApiInterface by lazy {
        myRetrofitClient.build().create(ApiInterface::class.java)
    }
}
