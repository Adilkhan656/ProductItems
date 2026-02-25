package com.example.dummyjsonapp.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyjsonapp.viewmodel.ProductViewModel
import com.example.dummyjsonapp.repo.ProductRepo

class ProductFactory(val repository: ProductRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
