package com.example.dummyjsonapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dummyjsonapp.datas.productItems
import com.example.dummyjsonapp.repo.ProductRepo
import kotlinx.coroutines.flow.Flow

class ProductViewModel(val productRepo: ProductRepo): ViewModel() {
    val products: Flow<PagingData<productItems>> =
        productRepo.getProductsPager().cachedIn(viewModelScope)

}
