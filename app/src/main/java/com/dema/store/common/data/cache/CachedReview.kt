package com.dema.store.common.data.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dema.store.common.domain.model.product.Image
import com.dema.store.common.domain.model.product.Review

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = CachedProductWithDetails::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productId")]
)
data class CachedReview(
    val id: Long = 0,
    val productId: Long,
    val userName: String,
    val userImage: String,
    val date: String,
    val review: String,
    val rate: Int,
) {
    companion object {
        fun fromDomain(productId: Long, review: Review): CachedReview {
            val (id, userName, userImage, date, rev, rate) = review

            return CachedReview(
                productId = productId,
                id = id,
                userName = userName,
                userImage = userImage,
                date = date,
                review = rev,
                rate = rate
            )
        }
    }

    fun toDomain(): Review = Review(id, userName, userImage, date, review, rate)
}
