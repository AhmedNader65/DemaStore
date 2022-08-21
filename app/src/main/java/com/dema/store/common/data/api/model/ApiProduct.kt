package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.product.Details
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.domain.model.product.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiProduct(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "category_id") val categoryId: Long?,
    @field:Json(name = "category_name") val categoryName: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "photo") val photo: String?,
    @field:Json(name = "tags") val tags: List<String>?,
    @field:Json(name = "price") val price: String?,
    @field:Json(name = "price_discount") val priceDiscount: String?
)

@JsonClass(generateAdapter = true)
data class ApiProductDetails(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "category_id") val categoryId: Long?,
    @field:Json(name = "category_name") val categoryName: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "colors") val colors: List<String>?,
    @field:Json(name = "size") val size: String?,
    @field:Json(name = "materials") val materials: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "price") val price: String?,
    @field:Json(name = "price_discount") val priceDiscount: String?,
    @field:Json(name = "photo") val photo: String?,
    @field:Json(name = "photos") val photos: List<String>?,
    @field:Json(name = "tags") val tags: List<String>?,
    @field:Json(name = "reviews") val reviews: List<ApiReviews>?,
    @field:Json(name = "related_products") val relatedProducts: List<ApiProduct>?,
    @field:Json(name = "liked") val isLiked: Boolean?
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

fun ApiProduct.mapToDomain(): Product {
    return Product(
        id ?: throw MappingException("Product ID cannot be null"),
        name.orEmpty(),
        photo.orEmpty(),
        categoryId ?: throw MappingException("Category ID cannot be null"),
        categoryName.orEmpty(),
        price.orEmpty(),
        priceDiscount.orEmpty(),
        tags.orEmpty()
    )
}

fun ApiProductDetails.mapToDomain(): ProductWithDetails {
    return ProductWithDetails(
        id ?: throw MappingException("Product ID cannot be null"),
        name.orEmpty(),
        photo.orEmpty(),
        categoryId ?: throw MappingException("Category ID cannot be null"),
        categoryName.orEmpty(),
        price.orEmpty(),
        priceDiscount.orEmpty(),
        Details(
            description.orEmpty(),
            size.orEmpty(),
            materials.orEmpty(),
            isLiked ?: false,
            colors.orEmpty(),
            relatedProducts?.map {
                it.mapToDomain()
            } ?: emptyList(),
            reviews?.map {
                it.mapToDomain()
            } ?: emptyList()

        ),
        tags.orEmpty()
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