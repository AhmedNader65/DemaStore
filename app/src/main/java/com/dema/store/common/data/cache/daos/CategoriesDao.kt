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

package com.dema.store.common.data.cache.daos

import androidx.room.*
import com.dema.store.common.data.cache.model.CachedCategory
import com.dema.store.common.data.cache.model.CachedUpdateCategory
import com.dema.store.common.domain.model.category.UpdateCategory
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg organization: CachedCategory)

    @Update(entity = CachedCategory::class)
    abstract suspend fun update(updateCategory: CachedUpdateCategory)

    @Query("UPDATE categories SET name = :name, slug = :slug, icon = :icon, image = :image WHERE id=:id")
    abstract suspend fun update(id: Long, name: String, slug: String, icon: String, image: String)

    @Transaction
    @Query("SELECT * FROM categories")
    abstract fun getAllCategories(): Flow<List<CachedCategory>>

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :id")
    abstract fun getCategoryById(id: Long): CachedCategory?


    suspend fun insertOrUpdate(updatedCategory: CachedUpdateCategory) {
        val itemsFromDB = getCategoryById(updatedCategory.id)
        if (itemsFromDB != null)
            update(updatedCategory)
        else
            insert(updatedCategory.toCachedCategory())
    }

    suspend fun insertOrUpdate(vararg cachedCategories: CachedCategory) {
        cachedCategories.forEach { cachedCategory ->

            val itemsFromDB = getCategoryById(cachedCategory.id)
            if (itemsFromDB != null)
                update(
                    cachedCategory.id,
                    cachedCategory.name,
                    cachedCategory.slug,
                    cachedCategory.icon,
                    cachedCategory.image
                )
            else
                insert(cachedCategory)
        }
    }
}