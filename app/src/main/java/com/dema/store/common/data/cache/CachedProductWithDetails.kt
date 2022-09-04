package com.dema.store.common.data.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dema.store.common.domain.model.product.Details
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.domain.model.product.Review

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CachedCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("organizationId")]
)
data class CachedProductWithDetails(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val sku: String,
    val name: String,
    val categoryId: Long,
    val categoryName: String,
    val price: String,
    val regularPrice: String,
    val inStock: Boolean,
    val isInCart: Boolean,
    val isLiked: Boolean,
    val description: String,
    val size: String,
    val materials: String,
) {
    companion object {
        fun fromDomain(domainModel: ProductWithDetails): CachedProductWithDetails {
            val details = domainModel.details

            return CachedProductWithDetails(
                id = domainModel.id,
                sku = domainModel.sku,
                name = domainModel.name,
                categoryId = domainModel.categoryId,
                categoryName = domainModel.categoryName,
                price = domainModel.price,
                regularPrice = domainModel.regularPrice,
                inStock = domainModel.inStock,
                isInCart = domainModel.isInCart,
                isLiked = domainModel.isLiked,
                description = details.description,
                size = details.size,
                materials = details.materials
            )
        }
    }

    fun toDomain(
        reviews: List<CachedReview>,
        images: List<CachedImage>,
        image: CachedImage,
        category: CachedCategory
    ): ProductWithDetails {
        return ProductWithDetails(
            id = id,
            name = name,
            sku = sku,
            image = image.toDomain(),
            categoryId = category.id,
            categoryName = category.name,
            price = price,
            regularPrice = regularPrice,
            details = mapDetails(reviews, images),
            inStock = inStock,
            isInCart = isInCart,
            isLiked = isLiked
        )
    }

    fun toAnimalDomain(
        image: CachedImage,
        category: CachedCategory
    ): Product {

        return Product(
            id = id,
            name = name,
            sku = sku,
            image = image.toDomain(),
            categoryId = category.id,
            categoryName = category.name,
            price = price,
            regularPrice = regularPrice,
            inStock = inStock,
            isInCart = isInCart,
            isLiked = isLiked
        )
    }

    private fun mapDetails(
        reviews: List<CachedReview>,
        images: List<CachedImage>
    ): Details {
        return Details(
            description = description,
            size = size,
            materials = materials,
            isLiked = isLiked,
            reviews = reviews.map { it.toDomain() },
            images = images.map { it.toDomain() }
        )
    }
}