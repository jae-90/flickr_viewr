package com.jw.flickrviewr.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class for a SearchTerm.
 * @param index     index of the term
 * @param term      searched term
 */
@Entity
data class SearchTerm(
    @PrimaryKey(autoGenerate = true) val index: Int? = null,
    @ColumnInfo(name = "term") val term: String
)