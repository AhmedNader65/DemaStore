package com.dema.store.common.data.api

import com.dema.store.common.data.api.model.ApiPaginatedProducts
import com.dema.store.common.data.api.model.ApiProduct
import retrofit2.http.GET

interface DemaApi {

    @GET(ApiConstants.PRODUCTS_ENDPOINT)
    suspend fun getProducts(): ApiPaginatedProducts
}