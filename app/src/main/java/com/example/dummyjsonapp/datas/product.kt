package com.example.dummyjsonapp.datas

import com.google.gson.annotations.SerializedName

data class product(

    @SerializedName("products")
    val product: List<productItems> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0
)
