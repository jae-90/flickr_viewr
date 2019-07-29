package com.jw.flickrviewr.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jw.flickrviewr.data.source.SearchRepository
import com.jw.flickrviewr.data.source.local.SearchTerm
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * ViewModel for the Search screen.
 */
class SearchViewModel internal constructor(private val repository: SearchRepository) : ViewModel() {

    var terms: List<SearchTerm> = repository.getTerms()

    fun addToRecentSearchList(term: String) {
        viewModelScope.launch {
            for (searchTerm in terms) {
                if (searchTerm.term == term)
                    return@launch
            }
            repository.addTerm(SearchTerm(null, term))
        }
    }

    fun deleteTermFromSearchList(term: String) {
        viewModelScope.launch {
            for (searchTerm in terms) {
                if (searchTerm.term == term)
                    repository.deleteTerm(searchTerm)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}