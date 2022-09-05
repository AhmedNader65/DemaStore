package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.category.Category
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCategoryContainer(
    @field:Json(name = "data") val data: List<ApiCategory>
)
@JsonClass(generateAdapter = true)
data class ApiCategory(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "slug") val slug: String?,
    @field:Json(name = "category_icon_path") val icon: String?,
    @field:Json(name = "image_url") val image: String?,
)

fun ApiCategory.mapToDomain(): Category {
    return Category(
        id ?: throw MappingException("Category ID cannot be null"),
        name.orEmpty(),
        slug.orEmpty(),
        icon.orEmpty(),
        image.orEmpty()
    )
}