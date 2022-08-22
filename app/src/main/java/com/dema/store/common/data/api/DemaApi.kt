package com.dema.store.common.data.api

import com.dema.store.common.data.api.model.ApiCategory
import com.dema.store.common.data.api.model.ApiPaginatedProducts
import retrofit2.http.GET

interface DemaApi {

    @GET(ApiConstants.PRODUCTS_ENDPOINT)
    suspend fun getProducts(): ApiPaginatedProducts

    @GET(ApiConstants.PRODUCTS_ENDPOINT)
    suspend fun getCategories(): ApiCategory
}