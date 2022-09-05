package com.dema.store.common.data.api

import com.dema.store.common.data.api.ApiParameters.CREATING_TOKEN
import com.dema.store.common.data.api.ApiParameters.NO_AUTH_HEADER
import com.dema.store.common.data.api.model.ApiCategory
import com.dema.store.common.data.api.model.ApiPaginatedProducts
import com.dema.store.common.domain.model.category.Category
import retrofit2.http.*

interface DemaApi {

    @GET(ApiConstants.PRODUCTS_ENDPOINT)
    suspend fun getProducts(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int,
        @Query(ApiParameters.CATEGORY_ID) category: Int,
    ): ApiPaginatedProducts

    @GET(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun getCategories(): List<ApiCategory>

    @Headers(NO_AUTH_HEADER, CREATING_TOKEN)
    @POST(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun login(): ApiCategory
}