package com.jw.flickrviewr.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jw.flickrviewr.data.source.GalleryRepository
import com.jw.flickrviewr.util.Const.PHOTO_COUNT_INCREMENT_VALUE
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * ViewModel for the main Gallery screen.
 */
class GalleryViewModel(private val repository: GalleryRepository, val tag: String) : ViewModel() {

    private val photoStash = repository.photoStash

    val photos = repository.photos

    var photoCount = repository.photoCount

    init {
        if (tag.isNotBlank())
            fetchPhotosOf(tag)
    }

    private fun fetchPhotosOf(tag: String) {
        viewModelScope.launch {
            repository.initPhotos(tag)
        }
    }

    fun loadMorePhotos() {
        photoCount += PHOTO_COUNT_INCREMENT_VALUE

        photoStash.value?.let {
            if (photoCount >= it.size)
                photoCount = it.size

            photos.postValue(it.take(photoCount))
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}