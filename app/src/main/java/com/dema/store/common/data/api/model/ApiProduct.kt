package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.product.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiProductDetails(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "sku") val sku: String?,
    @field:Json(name = "category_id") val categoryId: Long?,
    @field:Json(name = "category_name") val categoryName: String?,
    @field:Json(name = "url_key") val url: String?,
    @field:Json(name = "colors") val colors: List<String>?,
    @field:Json(name = "size") val size: String?,
    @field:Json(name = "materials") val materials: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "price") val price: String?,
    @field:Json(name = "regular_price") val regularPrice: String?,
    @field:Json(name = "base_image") val photo: ApiImage,
    @field:Json(name = "images") val photos: List<ApiImage>,
    @field:Json(name = "reviews") val reviews: List<ApiReviews>?,
    @field:Json(name = "related_products") val relatedProducts: List<ApiProductDetails>?,
    @field:Json(name = "is_wishlisted") val isLiked: Boolean = false,
    @field:Json(name = "is_item_in_cart") val isInCart: Boolean = false,
    @field:Json(name = "in_stock") val inStock: Boolean = false,
)

@JsonClass(generateAdapter = true)
data class ApiReviews(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "user_name") val userName: String?,
    @field:Json(name = "user_image") val userImage: String?,
    @field:Json(name = "date") val date: String?,
    @field:Json(name = "review") val review: String?,
    @field:Json(name = "rate") val rate: Int?
)

@JsonClass(generateAdapter = true)
data class ApiImage(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "path") val path: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "original_image_url") val original: String?,
    @field:Json(name = "small_image_url") val small: String?,
    @field:Json(name = "medium_image_url") val medium: String?,
    @field:Json(name = "large_image_url") val large: String?
) {
}

fun ApiProductDetails.mapToDomainProduct(): Product {
    return Product(
        id ?: throw MappingException("Product ID cannot be null"),
        sku.orEmpty(),
        name.orEmpty(),
        photo.mapToDomain(),
        categoryId ?: throw MappingException("Category ID cannot be null"),
        categoryName.orEmpty(),
        price.orEmpty(),
        regularPrice.orEmpty(),
        inStock,
        isInCart,
        isLiked
    )
}

fun ApiProductDetails.mapToDomainProductWithDetails(): ProductWithDetails {
    return ProductWithDetails(
        id ?: throw MappingException("Product ID cannot be null"),
        sku.orEmpty(),
        name.orEmpty(),
        photo.mapToDomain(),
        categoryId ?: throw MappingException("Category ID cannot be null"),
        categoryName.orEmpty(),
        price.orEmpty(),
        regularPrice.orEmpty(),
        Details(
            description.orEmpty(),
            size.orEmpty(),
            materials.orEmpty(),
            isLiked,
            reviews?.map {
                it.mapToDomain()
            } ?: emptyList()

        ),
        inStock,
        isInCart,
        isLiked,
    )
}

fun ApiReviews.mapToDomain(): Review {
    return Review(
        id ?: throw MappingException("Review ID cannot be null"),
        userName.orEmpty(),
        userImage.orEmpty(),
        date.orEmpty(),
        review.orEmpty(),
        rate ?: 0
    )
}
fun ApiImage.mapToDomain(): Image {
    return Image(
        id ?: throw MappingException("Review ID cannot be null"),
        path.orEmpty(),
        url.orEmpty(),
        original.orEmpty(),
        small.orEmpty(),
        medium.orEmpty(),
        large.orEmpty()
    )
}