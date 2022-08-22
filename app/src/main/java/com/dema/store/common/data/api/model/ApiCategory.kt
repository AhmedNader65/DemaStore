package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.product.Details
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.domain.model.product.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCategory(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "icon") val icon: String?,
    @field:Json(name = "image") val image: String?,
    @field:Json(name = "productCount") val productCount: Int?,
)

fun ApiCategory.mapToDomain(): Category {
    return Category(
        id ?: throw MappingException("Category ID cannot be null"),
        name.orEmpty(),
        icon.orEmpty(),
        image.orEmpty(),
        productCount ?: 0
    )
}