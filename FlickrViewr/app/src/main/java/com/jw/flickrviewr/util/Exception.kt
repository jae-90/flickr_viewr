package com.jw.flickrviewr.util

/**
 * ViewrException class to hold custom exceptions.
 */
open class ViewrException(message: String? = null) : Exception(message)

class DataFetchException : ViewrException("Error fetching data. Check your internet connection.")