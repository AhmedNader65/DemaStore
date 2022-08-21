package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.model.pagination.Pagination
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginatedProducts(
    @field:Json(name = "products") val products: List<ApiProduct>?,
    @field:Json(name = "pagination") val pagination: ApiPagination?
)

@JsonClass(generateAdapter = true)
data class ApiPagination(
    @field:Json(name = "count_per_page") val countPerPage: Int?,
    @field:Json(name = "total_count") val totalCount: Int?,
    @field:Json(name = "current_page") val currentPage: Int?,
    @field:Json(name = "total_pages") val totalPages: Int?
)

fun ApiPagination.mapToDomain(): Pagination {
    return Pagination(
        currentPage = currentPage ?: 0,
        totalPages = totalPages ?: 0
    )
}

fun ApiPaginatedProducts.mapToDomain(): PaginatedProducts {
    return PaginatedProducts(
        products?.map {
            it.mapToDomain()
        } ?: emptyList(),
        pagination = pagination?.mapToDomain() ?: Pagination(1, 1)
    )
}