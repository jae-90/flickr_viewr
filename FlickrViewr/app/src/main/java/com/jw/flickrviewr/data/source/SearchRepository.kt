package com.jw.flickrviewr.data.source

import com.jw.flickrviewr.data.source.local.SearchTerm
import com.jw.flickrviewr.data.source.local.TermDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load saved search terms from the Room Database.
 */
class SearchRepository private constructor(private val termDao: TermDao) {

    suspend fun addTerm(term: SearchTerm) {
        withContext(Dispatchers.IO) {
            termDao.insertTerm(term)
        }
    }

    suspend fun deleteTerm(term: SearchTerm) {
        withContext(Dispatchers.IO) {
            termDao.delete(term)
        }
    }

    suspend fun nukeTable() {
        withContext(Dispatchers.IO) {
            termDao.nukeTable()
        }
    }

    fun getTerms() = termDao.getTerms()

    companion object {
        @Volatile private var instance: SearchRepository? = null
        fun getInstance(termDao: TermDao): SearchRepository {
            return instance ?: synchronized(this) {
                instance
                    ?: SearchRepository(termDao).also { instance = it }
            }
        }
    }

}