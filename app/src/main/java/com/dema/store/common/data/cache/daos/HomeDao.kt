package com.dema.store.common.data.cache.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dema.store.common.data.cache.model.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HomeDao {

    @Transaction
    @Query(
        "SELECT home.*,products.*,images.* FROM home" +
                " LEFT JOIN products ON home.pId == products.id" +
                " LEFT JOIN images ON home.pId == images.productId" +
                " GROUP BY home.homeId"
    )
    abstract fun getHome(): Flow<List<CachedHomeAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHome(
        vararg home: CachedHome
    )

}
