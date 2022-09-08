package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.model.pagination.Pagination
import com.dema.store.home.domain.model.HomeProducts
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginatedProducts(
    @field:Json(name = "data") val products: List<ApiProductDetails>?,
    @field:Json(name = "meta") val pagination: ApiPagination?
)

@JsonClass(generateAdapter = true)
data class ApiHomeProducts(
    @field:Json(name = "new") val newProducts: List<ApiProductDetails>?,
    @field:Json(name = "popular") val popularProducts: List<ApiProductDetails>?,
    @field:Json(name = "sale") val saleProducts: List<ApiProductDetails>?,
)

@JsonClass(generateAdapter = true)
data class ApiPagination(
    @field:Json(name = "per_page") val countPerPage: Int?,
    @field:Json(name = "total") val totalCount: Int?,
    @field:Json(name = "current_page") val currentPage: Int?,
    @field:Json(name = "last_page") val totalPages: Int?
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
            it.mapToDomainProductWithDetails()
        } ?: emptyList(),
        pagination = pagination?.mapToDomain() ?: Pagination(1, 1)
    )
}
fun ApiHomeProducts.mapToDomain(): HomeProducts {
    return HomeProducts(
        newProducts?.map {
            it.mapToDomainProductWithDetails()
        } ?: emptyList(),
        popularProducts?.map {
            it.mapToDomainProductWithDetails()
        } ?: emptyList(),
        saleProducts?.map {
            it.mapToDomainProductWithDetails()
        } ?: emptyList(),
    )
}