package com.jw.flickrviewr.data.source

import com.jw.flickrviewr.data.Photo
import com.jw.flickrviewr.data.Result

/**
 * Main entry point for accessing photo data from Flickr API.
 */
interface GalleryDataSource {

    suspend fun getPhotos(tag: String): Result<List<Photo>>

}