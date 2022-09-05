package com.dema.store.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dema.store.common.domain.model.category.Category

@Entity(tableName = "categories")
data class CachedCategory(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val slug: String,
    val icon: String,
    val image: String
) {
    companion object {
        fun fromDomain(domainModel: Category): CachedCategory {

            return CachedCategory(
                id = domainModel.id,
                name = domainModel.name,
                slug = domainModel.slug,
                icon = domainModel.icon,
                image = domainModel.image
            )
        }
    }

    fun toDomain(): Category {
        return Category(
            id,
            name,
            slug,
            icon,
            image
        )
    }
}
