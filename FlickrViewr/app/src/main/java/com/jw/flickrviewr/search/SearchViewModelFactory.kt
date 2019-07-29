package com.jw.flickrviewr.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jw.flickrviewr.data.source.SearchRepository

/**
 * Factory for Search viewmodel.
 */
class SearchViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = SearchViewModel(repository) as T
    
}
