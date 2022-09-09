/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.dema.store.common.data.cache.model

import androidx.room.Embedded
import androidx.room.Relation
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.category.UpdateCategory
import com.dema.store.common.domain.model.product.Details
import com.dema.store.common.domain.model.product.ProductWithDetails

data class CachedProductAggregate(
    @Embedded
    val product: CachedProductWithDetails,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val photos: List<CachedImage?>,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CachedCategory,

    ) {
    companion object {
        fun fromDomain(
            productWithDetails: ProductWithDetails,
            category: UpdateCategory
        ): CachedProductAggregate {
            return CachedProductAggregate(
                product = CachedProductWithDetails.fromDomain(productWithDetails),
                photos = productWithDetails.details.images.map {
                    CachedImage.fromDomain(productWithDetails.id, it)
                },
                category = CachedUpdateCategory.fromDomain(category).toCachedCategory()
            )
        }
    }

    fun toDomain(): ProductWithDetails {

        return ProductWithDetails(
            id = product.id,
            name = product.name,
            sku = product.sku,
            image = photos.first()?.toDomain(),
            categoryId = category.id,
            categoryName = category.name,
            price = product.price,
            regularPrice = product.regularPrice,
            details = mapDetails( photos),
            isNew = product.isNew,
            isSale = product.isSale,
            isPopular = product.isPopular,
            inStock = product.inStock,
            isInCart = product.isInCart,
            isLiked = product.isLiked
        )
    }

    private fun mapDetails(
        images: List<CachedImage?>
    ): Details {
        return Details(
            description = product.description,
            size = product.size,
            materials = product.materials,
            isLiked = product.isLiked,
            images = images.map { it?.toDomain() }
        )
    }
}
