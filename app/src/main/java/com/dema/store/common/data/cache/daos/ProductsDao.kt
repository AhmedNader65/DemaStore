package com.dema.store.common.data.cache.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dema.store.common.data.cache.model.CachedImage
import com.dema.store.common.data.cache.model.CachedProductAggregate
import com.dema.store.common.data.cache.model.CachedProductWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProductsDao {

    @Transaction
    @Query("SELECT * FROM products")
    abstract fun getAllProducts(): Flow<List<CachedProductAggregate>>

    @Transaction
    @Query("SELECT * FROM products where categoryId = :categoryId")
    abstract fun getCategoryProduct(categoryId: Long): Flow<List<CachedProductAggregate>>

    @Transaction
    @Query("SELECT * FROM products where isPopular = :featured")
    abstract fun getFeaturedProducts(featured: Boolean): LiveData<List<CachedProductAggregate>>

    @Transaction
    @Query("SELECT * FROM products where isNew = :newState")
    abstract fun getNewProducts(newState: Boolean): LiveData<List<CachedProductAggregate>>

    @Transaction
    @Query("SELECT * FROM products where isSale = :sale")
    abstract fun getSaleProducts(sale: Boolean): LiveData<List<CachedProductAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProductAggregate(
        products: CachedProductWithDetails,
        photos: List<CachedImage?>,
    )

    suspend fun insertProductsWithDetails(vararg productAggregates: CachedProductAggregate) {
        for (productAggregate in productAggregates) {
            insertProductAggregate(
                productAggregate.product,
                productAggregate.photos,
            )
        }
    }
}
