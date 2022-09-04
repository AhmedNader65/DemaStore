package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.category.Category
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAuthenticator(
    @field:Json(name = "token") val token: String?,
    @field:Json(name = "message") val message: String?,
    @field:Json(name = "data") val data: ApiCustomer?
) {

    companion object {
        val INVALID = ApiAuthenticator("", "", null)
    }

    fun isValid(): Boolean {
        return token != null && data != null
    }
}

