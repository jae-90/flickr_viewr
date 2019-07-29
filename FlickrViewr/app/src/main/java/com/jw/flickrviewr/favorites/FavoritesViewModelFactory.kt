package com.jw.flickrviewr.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory for Favorites viewmodel.
 */
class FavoritesViewModelFactory(private val app: Application) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = FavoritesViewModel(app) as T

}
