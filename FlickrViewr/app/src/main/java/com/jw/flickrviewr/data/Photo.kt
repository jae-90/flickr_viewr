package com.jw.flickrviewr.data

import com.jw.flickrviewr.util.Const.BASE_IMAGE_URL
import com.jw.flickrviewr.util.Const.JPG_EXT

/**
 * Model class for the photo from the photos data object from teh Flickr API.
 * @param id            id of the fetched photo.
 * @param title         title of the fetched photo.
 * @param server        server of the fetched photo.
 * @param secret        secret of the fetched photo.
 * @param url           url of the fetched photo.
 */
data class Photo(val id: String = "",
                 val title: String = "",
                 val server: String = "",
                 val secret: String = "",
                 val url: String = "") {

    fun url(): String {
        return if (url.isBlank())
            "$BASE_IMAGE_URL$server/${id}_$secret$JPG_EXT"
        else url
    }

    override fun toString(): String =
        "Photo(id='$id', title='$title', server='$server', secret='$secret', url='$url')"

}