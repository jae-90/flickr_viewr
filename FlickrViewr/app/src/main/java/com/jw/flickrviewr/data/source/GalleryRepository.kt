package com.jw.flickrviewr.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jw.flickrviewr.data.Photo
import com.jw.flickrviewr.data.Result
import com.jw.flickrviewr.util.Const.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load photos from the Flickr API.
 */
class GalleryRepository(private val dataSource: GalleryDataSource) {

    private val TAG by lazy { GalleryRepository::class.java.simpleName }

    val photos: MutableLiveData<List<Photo>> by lazy { MutableLiveData<List<Photo>>() }

    val photoStash: MutableLiveData<List<Photo>> by lazy { MutableLiveData<List<Photo>>() }

    var photoCount: Int = PHOTO_COUNT_INCREMENT_VALUE

    suspend fun initPhotos(tag: String) {
        withContext(Dispatchers.IO) {
            val result = dataSource.getPhotos(tag)
            if (result is Result.Success) {
                photoStash.postValue(result.data)
                photos.postValue(result.data.take(photoCount))
            } else {
                Log.e(TAG, result.toString())
            }
        }
    }

    companion object {
        @Volatile private var instance: GalleryRepository? = null
        fun getInstance(dataSource: GalleryDataSource): GalleryRepository {
            return instance ?: synchronized(this) {
                instance
                    ?: GalleryRepository(dataSource).apply { instance = this }
            }
        }
    }

}