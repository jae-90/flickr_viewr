package com.jw.flickrviewr.data.interceptor

import com.jw.flickrviewr.util.Const.*
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to intercept api_key. API_KEY should be stored in local.properties for production.
 */
class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        return chain.proceed(chain.request().newBuilder().url(newUrl).build())
    }

}