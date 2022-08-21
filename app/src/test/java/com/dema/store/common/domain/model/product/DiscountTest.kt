package com.dema.store.common.domain.model.product

import org.junit.Test

class DiscountTest {

    @Test
    fun product_getHasDiscount_hasDifferentPrices() {
        // GIVEN
        val product = Product(1, "TestingProduct", "Image", "Category", "50", "45", emptyList())
        val expectedValue = true
        // WHEN
        val hasDiscount = product.hasDiscount
        // THEN
        assert(expectedValue == hasDiscount)
    }
    @Test
    fun product_getHasDiscount_hasSamePrices() {
        // GIVEN
        val product = Product(1, "TestingProduct", "Image", "Category", "50", "50", emptyList())
        val expectedValue = false
        // WHEN
        val hasDiscount = product.hasDiscount
        // THEN
        assert(expectedValue == hasDiscount)
    }

    @Test
    fun product_getHasDiscount_hasSecondPriceAsZero() {
        // GIVEN
        val product = Product(1, "TestingProduct", "Image", "Category", "50", "0", emptyList())
        val expectedValue = false
        // WHEN
        val hasDiscount = product.hasDiscount
        // THEN
        assert(expectedValue == hasDiscount)
    }
}