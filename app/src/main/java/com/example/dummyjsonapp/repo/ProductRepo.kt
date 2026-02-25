package com.example.dummyjsonapp.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dummyjsonapp.Network.RetrofitClient
import com.example.dummyjsonapp.datas.product
import com.example.dummyjsonapp.datas.productItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepo {

    var isLoading : MutableLiveData<Boolean> = MutableLiveData(true)
    fun getproduct(): MutableLiveData<List<productItems>> {
        isLoading.value = true

        val productList: MutableLiveData<List<productItems>> = MutableLiveData()

        val call = RetrofitClient.apiInterface.getproduct()
        call.enqueue(object : Callback<product>{
            override fun onResponse(
                call: Call<product?>?,
                response: Response<product?>?
            ) {
               if (response?.isSuccessful == true){
                   isLoading.value = false
                   val myproduct = response.body()
                   productList.value = myproduct?.product as List<productItems>?
               }
            }

            override fun onFailure(
                call: Call<product?>?,
                t: Throwable?
            ) {
                isLoading.value = false
                Log.d("myProduct", "onFailure: ${t?.message}")
            }

        })
return productList
    }



}