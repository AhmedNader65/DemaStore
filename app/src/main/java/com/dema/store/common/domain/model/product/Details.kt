package com.dema.store.common.domain.model.product

data class Details(
    val description: String,
    val size: String,
    val materials: String,
    var liked: Boolean,
    val availableColors: List<String>,
    val relatedProducts: List<Product>,
    val reviews: List<Review>
) {
}

data class Review(
    val id: Long,
    val userName: String,
    val userImage: String,
    val date: String,
    val review: String,
    val rate: Int
)