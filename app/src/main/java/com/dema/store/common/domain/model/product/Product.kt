package com.dema.store.common.domain.model.product

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val category: String,
    val price: String,
    val discountPrice: String,
    val tags: List<String>,
) {
    val hasDiscount = price == discountPrice
}

data class ProductWithDetails(
    val id: Long,
    val name: String,
    val image: String,
    val category: String,
    val price: String,
    val details: Details,
    val discountPrice: String,
    val tags: List<String>,
) {
    val hasDiscount = price == discountPrice
}
