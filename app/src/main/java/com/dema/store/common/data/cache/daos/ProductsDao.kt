package com.dema.store.common.data.cache.daos

import androidx.room.*
import com.dema.store.common.data.cache.model.CachedImage
import com.dema.store.common.data.cache.model.CachedProductAggregate
import com.dema.store.common.data.cache.model.CachedProductWithDetails
import com.dema.store.common.data.cache.model.CachedReview
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProductsDao {

    @Transaction
    @Query("SELECT * FROM products")
    abstract fun getAllProducts(): Flow<List<CachedProductAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProductAggregate(
        products: CachedProductWithDetails,
        photos: List<CachedImage?>,
        reviews: List<CachedReview>,
    )

    suspend fun insertProductsWithDetails(productAggregates: List<CachedProductAggregate>) {
        for (productAggregate in productAggregates) {
            insertProductAggregate(
                productAggregate.product,
                productAggregate.photos,
                productAggregate.reviews
            )
        }
    }
}
