package com.jw.flickrviewr.data

/**
 * Model class for the fetched data from Flickr API.
 * @param photos    photos object from the fetched data.
 */
data class FetchedData(val photos: Photos) {

    override fun toString(): String = "FetchedData(photos='$photos')"

}