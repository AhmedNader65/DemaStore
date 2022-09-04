package com.dema.store.common.data.api

object ApiConstants {
  const val BASE_ENDPOINT = "https://api.demo.com/v2/"
  const val PRODUCTS_ENDPOINT = "products"
  const val CATEGORIES_ENDPOINT = "categories?pagination=0"
  const val AUTH_ENDPOINT = "customer/login?token=true"
}

object ApiParameters {
  const val TOKEN_TYPE = "Bearer "
  const val AUTH_HEADER = "Authorization"
  const val NO_AUTH_HEADER = "NO_AUTH"
}