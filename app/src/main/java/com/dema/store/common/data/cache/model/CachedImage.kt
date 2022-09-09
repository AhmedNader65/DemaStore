package com.dema.store.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dema.store.common.domain.model.product.Image

@Entity(
    tableName = "images",
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
data class CachedImage(
    @PrimaryKey(autoGenerate = false)
    val imageId: Long = 0,
    val productId: Long,
    val path: String,
    val url: String,
    val original: String,
    val small: String,
    val medium: String,
    val large: String
) {
    companion object {
        fun fromDomain(productId: Long, photo: Image?): CachedImage? {
            photo?.let {
                val (id, path, url, original, small, medium, large) = photo

                return CachedImage(
                    productId = productId,
                    imageId = id,
                    path = path,
                    url = url,
                    original = original,
                    small = small,
                    medium = medium,
                    large = large
                )
            }.run {
                return null
            }

        }
    }

    fun toDomain(): Image = Image(imageId, path, url, original, small, medium, large)
}
