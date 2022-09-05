package com.dema.store.common.data.api.model

import com.dema.store.common.domain.model.product.Image
import org.junit.Assert.*

import org.junit.Test

class ImageTest {
    private val id = 10L
    private val path = "path"
    private val url = "url"
    private val smallPhoto = "smallPhoto"
    private val mediumPhoto = "mediumPhoto"
    private val largePhoto = "largePhoto"
    private val fullPhoto = "fullPhoto"
    private val invalidPhoto = "" // what’s tested in Photo.isValidPhoto()

    @Test
    fun getSmallestAvailablePhoto() {
        // Given
        val photo = Image(id, path, url, fullPhoto, smallPhoto, mediumPhoto, largePhoto)
        val expectedValue = smallPhoto
        // When
        val smallestPhoto = photo.getSmallestAvailablePhoto()
        // Then
        assertEquals(smallestPhoto, expectedValue)
    }

    @Test
    fun getSmallestAvailablePhoto_noSmallPhoto_returnsMedium() {
        // Given
        val photo = Image(id, path, url, fullPhoto, invalidPhoto, mediumPhoto, largePhoto)
        val expectedValue = mediumPhoto
        // When
        val smallestPhoto = photo.getSmallestAvailablePhoto()
        // Then
        assertEquals(smallestPhoto, expectedValue)
    }
}