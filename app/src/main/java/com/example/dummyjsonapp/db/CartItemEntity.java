package com.example.dummyjsonapp.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItemEntity {

    @PrimaryKey
    public int productId;

    @NonNull
    public String title;
    @NonNull
    public String description;
    @NonNull
    public String brand;
    @NonNull
    public String category;

    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;

    @NonNull
    public String availabilityStatus;
    @NonNull
    public String thumbnail;
    @NonNull
    public String shippingInformation;
    @NonNull
    public String returnPolicy;
    @NonNull
    public String warrantyInformation;
    @NonNull
    public String sku;

    public int minimumOrderQuantity;
    public int weight;

    @NonNull
    public String dimensionsJson;
    @NonNull
    public String tagsJson;
    @NonNull
    public String reviewsJson;
    @NonNull
    public String imagesJson;

    @NonNull
    public String barcode;
    @NonNull
    public String createdAt;
    @NonNull
    public String updatedAt;
    @NonNull
    public String qrCode;

    public int quantity;
    public long addedAt;

    public CartItemEntity(
            int productId,
            @NonNull String title,
            @NonNull String description,
            @NonNull String brand,
            @NonNull String category,
            double price,
            double discountPercentage,
            double rating,
            int stock,
            @NonNull String availabilityStatus,
            @NonNull String thumbnail,
            @NonNull String shippingInformation,
            @NonNull String returnPolicy,
            @NonNull String warrantyInformation,
            @NonNull String sku,
            int minimumOrderQuantity,
            int weight,
            @NonNull String dimensionsJson,
            @NonNull String tagsJson,
            @NonNull String reviewsJson,
            @NonNull String imagesJson,
            @NonNull String barcode,
            @NonNull String createdAt,
            @NonNull String updatedAt,
            @NonNull String qrCode,
            int quantity,
            long addedAt
    ) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.availabilityStatus = availabilityStatus;
        this.thumbnail = thumbnail;
        this.shippingInformation = shippingInformation;
        this.returnPolicy = returnPolicy;
        this.warrantyInformation = warrantyInformation;
        this.sku = sku;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.weight = weight;
        this.dimensionsJson = dimensionsJson;
        this.tagsJson = tagsJson;
        this.reviewsJson = reviewsJson;
        this.imagesJson = imagesJson;
        this.barcode = barcode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.qrCode = qrCode;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }
}
