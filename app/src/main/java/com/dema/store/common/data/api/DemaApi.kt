package com.dema.store.common.data.api

import com.dema.store.common.data.api.ApiParameters.CREATING_TOKEN
import com.dema.store.common.data.api.ApiParameters.NO_AUTH_HEADER
import com.dema.store.common.data.api.ApiParameters.NO_AUTH_VALUE
import com.dema.store.common.data.api.model.ApiCategory
import com.dema.store.common.data.api.model.ApiCategoryContainer
import com.dema.store.common.data.api.model.ApiHomeProducts
import com.dema.store.common.data.api.model.ApiPaginatedProducts
import com.dema.store.common.domain.model.category.Category
import retrofit2.http.*

interface DemaApi {

    @Headers("$NO_AUTH_HEADER:$NO_AUTH_VALUE")
    @GET(ApiConstants.PRODUCTS_ENDPOINT)
    suspend fun getProducts(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int,
        @Query(ApiParameters.CATEGORY_ID) category: Long,
    ): ApiPaginatedProducts

    @Headers("$NO_AUTH_HEADER:$NO_AUTH_VALUE")
    @GET(ApiConstants.HOME_ENDPOINT)
    suspend fun getHome(): ApiHomeProducts

    @GET(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun getCategories(): ApiCategoryContainer

    @Headers(NO_AUTH_HEADER, CREATING_TOKEN)
    @POST(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun login(): ApiCategory
}