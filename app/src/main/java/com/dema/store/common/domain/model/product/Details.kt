package com.dema.store.common.domain.model.product

data class Details(
    val description: String,
    val size: String,
    val materials: String,
    var isLiked: Boolean,
    val reviews: List<Review>,
    val images: List<Image?>
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