package com.dema.store.common.domain.model.product

data class Product(
    val id: Long,
    val sku: String?,
    val name: String,
    val image: Image?,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val regularPrice: String,
    val inStock: Boolean,
    val isInCart: Boolean,
    val isLiked: Boolean,
) {
    val hasDiscount = price != regularPrice
}

data class ProductWithDetails(
    val id: Long,
    val sku: String,
    val name: String,
    val image: Image?,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val regularPrice: String,
    val details: Details,
    val inStock: Boolean,
    val isInCart: Boolean,
    val isLiked: Boolean,
) {
    val hasDiscount = price != regularPrice
}

data class Image(
   val id: Long,
    val path: String,
    val url: String,
    val original: String,
    val small: String,
    val medium: String,
    val large: String
) {
    companion object {
        const val EMPTY_PHOTO = ""
    }

    fun getSmallestAvailablePhoto(): String { // 1
        return when {
            isValidPhoto(small) -> small!!
            isValidPhoto(medium) -> medium!!
            isValidPhoto(large) -> large!!
            isValidPhoto(original) -> original!!
            else -> EMPTY_PHOTO
        }
    }

    private fun isValidPhoto(photo: String?): Boolean { // 2
        return !photo.isNullOrEmpty()
    }
}

