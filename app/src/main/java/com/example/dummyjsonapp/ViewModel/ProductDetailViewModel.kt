package com.example.dummyjsonapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyjsonapp.datas.productItems
import com.example.dummyjsonapp.repo.ProductRepo
import java.util.Locale

class ProductDetailViewModel(val myrepo: ProductRepo) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = myrepo.isLoading
    val selectedProduct: MutableLiveData<productItems> = MutableLiveData()

    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val brand: MutableLiveData<String> = MutableLiveData()
    val category: MutableLiveData<String> = MutableLiveData()
    val priceText: MutableLiveData<String> = MutableLiveData()
    val discountText: MutableLiveData<String> = MutableLiveData()
    val ratingText: MutableLiveData<String> = MutableLiveData()
    val stockText: MutableLiveData<String> = MutableLiveData()
    val availabilityStatus: MutableLiveData<String> = MutableLiveData()
    val thumbnailUrl: MutableLiveData<String> = MutableLiveData()
    val shippingInfo: MutableLiveData<String> = MutableLiveData()
    val returnPolicy: MutableLiveData<String> = MutableLiveData()
    val warrantyInfo: MutableLiveData<String> = MutableLiveData()
    val skuText: MutableLiveData<String> = MutableLiveData()
    val minOrderQtyText: MutableLiveData<String> = MutableLiveData()
    val weightText: MutableLiveData<String> = MutableLiveData()
    val tagsText: MutableLiveData<String> = MutableLiveData()
    val dimensionsText: MutableLiveData<String> = MutableLiveData()
    val reviewsCountText: MutableLiveData<String> = MutableLiveData()

    fun setProduct(product: productItems) {
        selectedProduct.value = product

        title.value = product.title
        description.value = product.description
        brand.value = product.brand
        category.value = product.category
        priceText.value = String.format(Locale.US, "$%.2f", product.price)
        discountText.value = String.format(Locale.US, "-%.1f%%", product.discountPercentage)
        ratingText.value = String.format(Locale.US, "%.1f", product.rating)
        stockText.value = product.stock.toString()
        availabilityStatus.value = product.availabilityStatus
        thumbnailUrl.value = product.thumbnail
        shippingInfo.value = product.shippingInformation
        returnPolicy.value = product.returnPolicy
        warrantyInfo.value = product.warrantyInformation
        skuText.value = product.sku
        minOrderQtyText.value = product.minimumOrderQuantity.toString()
        weightText.value = product.weight.toString()
        tagsText.value = product.tags.joinToString(", ")
        dimensionsText.value = String.format(
            Locale.US,
            "W: %.1f  H: %.1f  D: %.1f",
            product.dimensions.width,
            product.dimensions.height,
            product.dimensions.depth
        )
        reviewsCountText.value = product.reviews.size.toString()
    }
}
