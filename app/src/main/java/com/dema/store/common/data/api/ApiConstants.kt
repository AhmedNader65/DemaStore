package com.dema.store.common.data.api

object ApiConstants {
  const val BASE_ENDPOINT = "http://192.168.1.3:8080/api/"
  const val PRODUCTS_ENDPOINT = "products"
  const val HOME_ENDPOINT = "home"
  const val CATEGORIES_ENDPOINT = "categories?pagination=0"
  const val AUTH_ENDPOINT = "customer/login?token=true"
}

object ApiParameters {
  const val TOKEN_TYPE = "Bearer "
  const val AUTH_HEADER = "Authorization"
  const val NO_AUTH_HEADER = "NO_AUTH"
  const val NO_AUTH_VALUE = "NA"
  const val CREATING_TOKEN = "CREATING_TOKEN"
  // PRODUCT
  const val PAGE = "page"
  const val LIMIT = "limit"
  const val CATEGORY_ID = "category_id"
}