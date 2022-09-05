package com.dema.store.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.dema.store.common.data.api.DemaApi
import com.dema.store.common.data.api.utils.FakeServer
import com.dema.store.common.data.cache.Cache
import com.dema.store.common.data.cache.DemaStoreDatabase
import com.dema.store.common.data.cache.RoomCache
import com.dema.store.common.data.di.CacheModule
import com.dema.store.common.data.di.PreferencesModule
import com.dema.store.common.data.preferences.Preferences
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.repositories.ProductsRepository
import com.raywenderlich.android.petsave.common.data.preferences.FakePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(
    PreferencesModule::class,
    CacheModule::class
) //not load the original PreferencesModule, replace it with a test module
class DemaStoreProductRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: ProductsRepository
    private lateinit var api: DemaApi

    @BindValue // This annotation handles the replacement and injection
    @JvmField // can be removed in future
    val preferences: Preferences = FakePreferences()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var cache: Cache

    @Inject
    lateinit var database: DemaStoreDatabase

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Module
    @InstallIn(SingletonComponent::class)
    object TestCacheModule {
        @Provides
        fun provideRoomDatabase(): DemaStoreDatabase {
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                DemaStoreDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    @Before
    fun setup() {
        fakeServer.start()

        preferences.deleteTokenInfo()
        preferences.putToken("validToken")
        hiltRule.inject()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(DemaApi::class.java)

        cache = RoomCache(database.productsDao(), database.categoriesDao())

        repository = DemaStoreProductRepository(
            api,
            cache
        )
    }

    @Test
    fun getProducts_success() = runBlocking {
        // Given
        val expectedProductId = 411L
        fakeServer.setHappyPathDispatcher()
        // When
        val paginatedProducts = repository.requestMoreProducts(1, 100, 0)
        // Then
        val product = paginatedProducts.products.first()
        assert(product.id == expectedProductId)
    }

    @Test
    fun getCategories() = runBlocking {
        // Given
        val expectedCategoryId = 21L
        fakeServer.setHappyPathDispatcher()
        // When
        val categories = repository.requestCategories()
        // Then
        val category = categories.first()
        assert(category.id == expectedCategoryId)
    }


    @Test
    fun storeCategory() {
        // Given
        val expectedCategoryId = 21L
        runBlocking {
            fakeServer.setHappyPathDispatcher()
            val categories = repository.requestCategories()
            val category = categories.first()
            // When
            repository.storeCategory(listOf(category))
            // Then
            val insertedValue = repository.getCategories().first()
            assert(insertedValue[0].id == expectedCategoryId)
        }
    }

    @Test
    fun storeProducts_success() {
        // Given
        val expectedProductId = 411L
        runBlocking {
            fakeServer.setHappyPathDispatcher()
            val category = Category(4, "", "", "", "")
            val paginatedProducts = repository.requestMoreProducts(1, 100, category.id)
            val product = paginatedProducts.products.first()
            // When
            repository.storeCategory(listOf(category))
            repository.storeProducts(category, listOf(product))
            // Then
            val insertedValue = repository.getProducts().first()
            assert(insertedValue[0].id == expectedProductId)
        }
    }

    @After
    fun teardown() {
        fakeServer.shutdown()
    }

}