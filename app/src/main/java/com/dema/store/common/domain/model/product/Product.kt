package com.dema.store.common.domain.model.product

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val discountPrice: String,
    val tags: List<String>,
) {
    val hasDiscount = price != discountPrice && discountPrice != "0"
}

data class ProductWithDetails(
    val id: Long,
    val name: String,
    val image: String,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val discountPrice: String,
    val details: Details,
    val tags: List<String>,
) {
    val hasDiscount = price != discountPrice && discountPrice != "0"
}
