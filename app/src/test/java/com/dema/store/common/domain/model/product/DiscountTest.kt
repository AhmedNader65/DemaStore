package com.dema.store.common.domain.model.product

import org.junit.Test

class DiscountTest {

    @Test
    fun product_getHasDiscount_hasDifferentPrices() {
        // GIVEN
        val product = Product(
            1,
            "sku",
            "TestingProduct",
            Image(1,"","","","","",""),
            50L,
            "Category",
            "50",
            "45",
            true,
            true,
            true,
        )
        val expectedValue = true
        // WHEN
        val hasDiscount = product.hasDiscount
        // THEN
        assert(expectedValue == hasDiscount)
    }

    @Test
    fun product_getHasDiscount_hasSamePrices() {
        // GIVEN
        val product = Product(1,
            "sku",
            "TestingProduct",
            Image(1,"","","","","",""),
            50L,
            "Category",
            "50",
            "50",
            true,
            true,
            true)
        val expectedValue = false
        // WHEN
        val hasDiscount = product.hasDiscount
        // THEN
        assert(expectedValue == hasDiscount)
    }

}