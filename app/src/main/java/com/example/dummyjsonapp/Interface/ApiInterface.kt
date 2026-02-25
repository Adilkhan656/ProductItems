package com.example.dummyjsonapp.Interface

import com.example.dummyjsonapp.datas.product
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    fun getproduct() : Call<product>
}