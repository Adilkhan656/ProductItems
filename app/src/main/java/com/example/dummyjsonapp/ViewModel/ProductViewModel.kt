package com.example.dummyjsonapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyjsonapp.datas.productItems
import com.example.dummyjsonapp.repo.ProductRepo

class ProductViewModel(val productRepo: ProductRepo): ViewModel() {
var isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    init {
       isLoading =  productRepo.isLoading
    }

    fun getproduct(): MutableLiveData<List<productItems>>{
        return productRepo.getproduct()

    }
}
