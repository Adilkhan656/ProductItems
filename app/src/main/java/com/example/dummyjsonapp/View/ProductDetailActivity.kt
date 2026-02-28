package com.example.dummyjsonapp.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.databinding.ActivityProductDetailBinding
import com.example.dummyjsonapp.datas.Review
import com.example.dummyjsonapp.datas.productItems
import com.example.dummyjsonapp.viewmodel.ProductDetailViewModel
import com.google.gson.Gson
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private var quantity: Int = 1
    private var currentProduct: productItems? = null

    companion object {
        const val EXTRA_PRODUCT_JSON = "extra_product_json"
        const val EXTRA_QUANTITY = "extra_quantity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        productDetailViewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            view.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
            insets
        }

        val productJson = intent.getStringExtra(EXTRA_PRODUCT_JSON)
        val product = productJson?.let { Gson().fromJson(it, productItems::class.java) }

        if (product != null) {
            currentProduct = product
            bindProduct(product)
            renderReviews(product.reviews)
            updateQtyUi()
            setupQuantityActions(product)
            setupActionButtons(product, productJson)
        }

        productDetailViewModel.cartMessage.observe(this) { message ->
            if (!message.isNullOrBlank()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupQuantityActions(product: productItems) {
        binding.btnMinusQty.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQtyUi()
            }
        }

        binding.btnPlusQty.setOnClickListener {
            if (quantity < product.stock) {
                quantity++
                updateQtyUi()
            } else {
                Toast.makeText(this, "Reached max stock", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActionButtons(product: productItems, productJson: String?) {
        binding.btnAddToCart.setOnClickListener {
            productDetailViewModel.addToCart(product, quantity)
        }

        binding.btnProceed.setOnClickListener {
            val payload = productJson ?: Gson().toJson(product)
            startActivity(
                Intent(this, CheckoutActivity::class.java).apply {
                    putExtra(EXTRA_PRODUCT_JSON, payload)
                    putExtra(EXTRA_QUANTITY, quantity)
                }
            )
        }
    }

    private fun updateQtyUi() {
        binding.tvQty.text = quantity.toString()
        binding.btnMinusQty.isEnabled = quantity > 1
    }

    private fun renderReviews(reviews: List<Review>) {
        binding.reviewsContainer.removeAllViews()

        if (reviews.isEmpty()) {
            val emptyText = TextView(this).apply {
                text = "No reviews yet"
                setTextColor(android.graphics.Color.parseColor("#6E7A89"))
                textSize = 13f
            }
            binding.reviewsContainer.addView(emptyText)
            return
        }

        val inflater = LayoutInflater.from(this)
        reviews.forEach { review ->
            val item = inflater.inflate(R.layout.item_review, binding.reviewsContainer, false)
            item.findViewById<TextView>(R.id.tvReviewerName).text = review.reviewerName
            item.findViewById<TextView>(R.id.tvReviewRating).text = "★ ${review.rating}"
            item.findViewById<TextView>(R.id.tvReviewComment).text = review.comment
            item.findViewById<TextView>(R.id.tvReviewMeta).text =
                "${review.reviewerEmail} • ${review.date.take(10)}"
            binding.reviewsContainer.addView(item)
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
    }
}
