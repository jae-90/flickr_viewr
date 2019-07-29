package com.jw.flickrviewr.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jw.flickrviewr.data.source.GalleryRepository

/**
 * Factory for main Gallery viewmodel.
 */
class GalleryViewModelFactory(
    private val repository: GalleryRepository,
    private val tag: String) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = GalleryViewModel(repository, tag) as T
    
}
