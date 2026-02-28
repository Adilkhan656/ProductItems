package com.example.dummyjsonapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dummyjsonapp.datas.productItems
import com.example.dummyjsonapp.db.AppDatabase
import com.example.dummyjsonapp.db.CartItemEntity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val cartDao = AppDatabase.getInstance(application).cartDao()
    private val gson = Gson()

    private val _cartMessage = MutableLiveData<String>()
    val cartMessage: LiveData<String> = _cartMessage

    fun addToCart(product: productItems, selectedQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val existingItem = cartDao.getByProductId(product.id)
                val mergedQuantity = (existingItem?.quantity ?: 0) + selectedQuantity

                cartDao.upsert(
                    CartItemEntity(
                        product.id,
                        product.title,
                        product.description,
                        product.brand,
                        product.category,
                        product.price,
                        product.discountPercentage,
                        product.rating,
                        product.stock,
                        product.availabilityStatus,
                        product.thumbnail,
                        product.shippingInformation,
                        product.returnPolicy,
                        product.warrantyInformation,
                        product.sku,
                        product.minimumOrderQuantity,
                        product.weight,
                        gson.toJson(product.dimensions),
                        gson.toJson(product.tags),
                        gson.toJson(product.reviews),
                        gson.toJson(product.images),
                        product.meta.barcode,
                        product.meta.createdAt,
                        product.meta.updatedAt,
                        product.meta.qrCode,
                        mergedQuantity,
                        System.currentTimeMillis()
                    )
                )

                _cartMessage.postValue("Added to cart")
            } catch (_: Exception) {
                _cartMessage.postValue("Unable to add item to cart")
            }
        }
    }
}
