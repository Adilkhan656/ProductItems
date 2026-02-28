package com.example.dummyjsonapp.Interface

import com.example.dummyjsonapp.datas.product
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): product
}
