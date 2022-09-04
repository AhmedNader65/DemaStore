package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.category.Category
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCustomer(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "first_name") val first_name: String?,
    @field:Json(name = "last_name") val last_name: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "gender") val gender: String?,
    @field:Json(name = "date_of_birth") val date_of_birth: String?,
    @field:Json(name = "phone") val phone: String?,
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "created_at") val created_at: String?,
)
