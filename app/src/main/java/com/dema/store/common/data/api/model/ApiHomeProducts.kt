package com.dema.store.common.data.api.model

import com.dema.store.home.domain.model.HomeProduct
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiHomeProducts(
    @field:Json(name = "new") val newProducts: List<ApiProductDetails>?,
    @field:Json(name = "featured") val popularProducts: List<ApiProductDetails>?,
    @field:Json(name = "sale") val saleProducts: List<ApiProductDetails>?,
)

fun ApiHomeProducts.mapToDomain(): MutableList<HomeProduct> {
    val listOfHomeProduct = mutableListOf<HomeProduct>()
    newProducts?.forEach {
        listOfHomeProduct.add(
            HomeProduct(type = "new", product = it.mapToDomainProductWithDetails())
        )
    }
    popularProducts?.forEach {
        listOfHomeProduct.add(
            HomeProduct(type = "popular", product = it.mapToDomainProductWithDetails())
        )
    }
    saleProducts?.forEach {
        listOfHomeProduct.add(
            HomeProduct(type = "sale", product = it.mapToDomainProductWithDetails())
        )
    }
    return listOfHomeProduct
}
