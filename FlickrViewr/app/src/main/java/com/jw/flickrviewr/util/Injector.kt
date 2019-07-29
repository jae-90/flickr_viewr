package com.jw.flickrviewr.util

import android.app.Application
import com.jw.flickrviewr.data.source.GalleryDataSourceImpl
import com.jw.flickrviewr.data.source.GalleryRepository
import com.jw.flickrviewr.data.source.SearchRepository
import com.jw.flickrviewr.data.source.local.AppDatabase
import com.jw.flickrviewr.favorites.FavoritesViewModelFactory
import com.jw.flickrviewr.gallery.GalleryViewModelFactory
import com.jw.flickrviewr.photo.PhotoViewModelFactory
import com.jw.flickrviewr.search.SearchViewModelFactory

/**
 * Static methods used to inject classes needed.
 */
object Injector {

    private fun getGalleryRepository(): GalleryRepository
            = GalleryRepository.getInstance(GalleryDataSourceImpl.getInstance())

    fun provideGalleryViewModelFactory(tag: String): GalleryViewModelFactory {
        val repository = getGalleryRepository()
        return GalleryViewModelFactory(repository, tag)
    }

    private fun getSearchRepository(app: Application): SearchRepository
        = SearchRepository.getInstance(AppDatabase.getInstance(app.applicationContext).termDao())

    fun provideSearchViewModelFactory(app: Application): SearchViewModelFactory
        = SearchViewModelFactory(getSearchRepository(app))

    fun provideFavoritesViewModelFactory(app: Application): FavoritesViewModelFactory
        = FavoritesViewModelFactory(app)

    fun providePhotoViewModelFactory(app: Application, url: String, id: String): PhotoViewModelFactory
        = PhotoViewModelFactory(app, url, id)

}
