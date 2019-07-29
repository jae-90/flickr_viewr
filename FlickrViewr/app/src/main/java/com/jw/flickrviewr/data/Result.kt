package com.jw.flickrviewr.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()

    override fun toString(): String =
        when (this) {
            is Success<*> -> "Success(data=$data)"
            is Error -> "Error(exception=$exception)"
        }

}