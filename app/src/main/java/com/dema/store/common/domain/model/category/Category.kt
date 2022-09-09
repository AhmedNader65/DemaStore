package com.dema.store.common.domain.model.category

import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.home.presentation.model.UIProductSlider

data class Category(
    val id: Long,
    val name: String,
    val slug: String,
    val icon: String,
    val image: String
) {

    fun mapToView(): UIProductSlider {
        return UIProductSlider(
            id,image
        )
    }
}
data class UpdateCategory(
    val id: Long,
    val name: String,
) {
}
