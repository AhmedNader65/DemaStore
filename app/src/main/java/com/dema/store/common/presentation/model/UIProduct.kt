package com.dema.store.common.presentation.model

data class UIProduct(
    val id: Long,
    val name: String,
    val image: String,
    val price: String,
    val regularPrice: String,
    val inStock: Boolean,
    val isInCart: Boolean,
    val isLiked: Boolean,
) {
    val hasDiscount = price != regularPrice
}
