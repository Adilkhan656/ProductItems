package com.example.dummyjsonapp.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.dummyjsonapp.datas.productItems
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.databinding.ActivityProductDetailBinding
import com.google.gson.Gson
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    companion object {
        const val EXTRA_PRODUCT_JSON = "extra_product_json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        val productJson = intent.getStringExtra(EXTRA_PRODUCT_JSON)
        val product = productJson?.let { Gson().fromJson(it, productItems::class.java) }
        if (product != null) bindProduct(product)

        binding.toolbar.setNavigationOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun bindProduct(product: productItems) = with(binding) {
        val imageUrl = product.images.firstOrNull().orEmpty().ifBlank { product.thumbnail }
        ivHeroImage.load(imageUrl) {
            crossfade(true)
            placeholder(android.R.color.darker_gray)
            error(android.R.color.darker_gray)
        }

        tvDetailTitle.text = product.title
        tvDetailDescription.text = product.description
        tvDetailPrice.text = String.format(Locale.US, "$%.2f", product.price)
        tvDetailDiscount.text = String.format(Locale.US, "-%.1f%%", product.discountPercentage)
        tvDetailRating.text = String.format(Locale.US, "★ %.1f", product.rating)
        tvDetailStock.text = "Stock: ${product.stock}"
        tvAvailabilityStatus.text = product.availabilityStatus
        tvBrand.text = "Brand: ${product.brand}"
        tvCategory.text = "Category: ${product.category}"
        tvSku.text = "SKU: ${product.sku}"
        tvMinimumOrderQty.text = "Minimum order quantity: ${product.minimumOrderQuantity}"
        tvWeight.text = "Weight: ${product.weight} g"
        tvShippingInformation.text = "Shipping: ${product.shippingInformation}"
        tvReturnPolicy.text = "Return policy: ${product.returnPolicy}"
        tvWarrantyInformation.text = "Warranty: ${product.warrantyInformation}"
        tvDimensionWidth.text = "Width: ${product.dimensions.width} cm"
        tvDimensionHeight.text = "Height: ${product.dimensions.height} cm"
        tvDimensionDepth.text = "Depth: ${product.dimensions.depth} cm"
        tvTags.text = product.tags.joinToString(", ")
        tvBarcode.text = "Barcode: ${product.meta.barcode}"
        tvCreatedAt.text = "Created at: ${product.meta.createdAt}"
        tvUpdatedAt.text = "Updated at: ${product.meta.updatedAt}"
        tvQrCode.text = "QR code: ${product.meta.qrCode}"
        tvReviewsCount.text = "Reviews count: ${product.reviews.size}"
    }
}
