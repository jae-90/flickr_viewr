package com.jw.flickrviewr.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object for the SearchTerm table.
 */
@Dao
interface TermDao {

    /**
     * Select all terms from the SearchTerm table.
     * @return all terms.
     */
    @Query("SELECT * FROM SearchTerm")
    fun getTerms(): List<SearchTerm>

    /**
     * Delete all terms from the SearchTerm table.
     */
    @Query("DELETE FROM SearchTerm")
    fun nukeTable()

    /**
     * Insert a search term in the database.
     * @param term to be inserted.
     */
    @Insert
    fun insertTerm(term: SearchTerm)

    /**
     * Delete a search term in the database.
     * @param term to be deleted.
     */
    @Delete
    fun delete(term: SearchTerm)

}