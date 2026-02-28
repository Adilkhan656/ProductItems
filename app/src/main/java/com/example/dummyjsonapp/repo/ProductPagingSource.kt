package com.example.dummyjsonapp.repo

import android.graphics.pdf.LoadParams
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dummyjsonapp.Interface.ApiInterface
import com.example.dummyjsonapp.datas.productItems

class ProductPagingSource(
    private val apiInterface: ApiInterface
) : PagingSource<Int, productItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, productItems> {
        return try {
            val skip = params.key ?: 0
            val pageSize = params.loadSize

            val response = apiInterface.getProducts(limit = pageSize, skip = skip)
            val products = response.product
            val nextSkip = skip + products.size
            val hasReachedEnd = response.total > 0 && nextSkip >= response.total
            val nextKey = if (products.isEmpty() || hasReachedEnd) null else nextSkip

            LoadResult.Page(
                data = products,
                prevKey = if (skip == 0) null else (skip - pageSize).coerceAtLeast(0),
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, productItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
