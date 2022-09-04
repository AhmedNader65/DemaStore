package com.dema.store.common.data.api

import com.dema.store.common.data.api.ApiParameters.CREATING_TOKEN
import com.dema.store.common.data.api.ApiParameters.NO_AUTH_HEADER
import com.dema.store.common.data.api.model.ApiCategory
import com.dema.store.common.data.api.model.ApiPaginatedProducts
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface DemaApi {

    @GET(ApiConstants.PRODUCTS_ENDPOINT)
    suspend fun getProducts(): ApiPaginatedProducts

    @GET(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun getCategories(): ApiCategory

    @Headers(NO_AUTH_HEADER,CREATING_TOKEN)
    @GET(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun login(): ApiCategory
}