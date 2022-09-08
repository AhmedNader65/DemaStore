package com.dema.store.home.presentation.model

import com.dema.store.common.presentation.model.UIProduct

data class UIHome(
    val newProducts: List<UIProduct>,
    val popularProducts: List<UIProduct>,
    val saleProducts: List<UIProduct>,
)