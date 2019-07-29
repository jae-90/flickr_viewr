package com.jw.flickrviewr.data.source

import com.jw.flickrviewr.data.Photo
import com.jw.flickrviewr.data.Result
import com.jw.flickrviewr.util.DataFetchException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load Photos from the data source.
 */
class GalleryDataSourceImpl private constructor(val service: ApiService) : GalleryDataSource {

    override suspend fun getPhotos(tag: String): Result<List<Photo>> = withContext(Dispatchers.IO) {
        try {
            val request = service.getPhotos(tag)
            val response = request.body()
            if (response!!.photos.photo.isNotEmpty())
                Result.Success(response.photos.photo)
            else
                Result.Error(DataFetchException())
        } catch (e: Exception) {
            Result.Error(DataFetchException())
        }
    }

    companion object {
        @Volatile private var instance: GalleryDataSourceImpl? = null
        fun getInstance(): GalleryDataSourceImpl {
            return instance ?: synchronized(this) {
                instance
                    ?: GalleryDataSourceImpl(ApiService.create()).apply { instance = this }
            }
        }
    }

}