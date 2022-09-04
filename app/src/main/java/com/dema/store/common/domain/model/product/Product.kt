package com.dema.store.common.domain.model.product

import com.squareup.moshi.Json

data class Product(
    val id: Long,
    val sku: String?,
    val name: String,
    val image: String,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val regularPrice: String,
    val inStock: Boolean,
    val isInCart: Boolean,
    val isLiked: Boolean,
    val tags: List<String>,
) {
    val hasDiscount = price != regularPrice
}

data class ProductWithDetails(
    val id: Long,
    val sku: String?,
    val name: String,
    val image: String,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val regularPrice: String,
    val details: Details,
    val tags: List<String>,
    val inStock: Boolean,
    val isInCart: Boolean,
    val isLiked: Boolean,
) {
    val hasDiscount = price != regularPrice
}
