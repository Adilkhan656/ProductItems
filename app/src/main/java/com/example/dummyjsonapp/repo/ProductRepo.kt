package com.example.dummyjsonapp.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.dummyjsonapp.Network.RetrofitClient
import com.example.dummyjsonapp.datas.productItems
import kotlinx.coroutines.flow.Flow

class ProductRepo {

    fun getProductsPager(pageSize: Int = 10): Flow<PagingData<productItems>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(RetrofitClient.apiInterface) }
        ).flow
    }
}
