package com.jw.flickrviewr.data

/**
 * Model class for the photos object from fetched data.
 * @param photo    photos object from the fetched data.
 */
data class Photos(val photo: List<Photo>) {

    override fun toString(): String = "Photos(photo=$photo)"

}