package com.jw.flickrviewr.data.source

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jw.flickrviewr.BuildConfig
import com.jw.flickrviewr.data.FetchedData
import com.jw.flickrviewr.data.interceptor.ApiKeyInterceptor
import com.jw.flickrviewr.util.Const.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service class that creates the Retrofit Service.
 */
interface ApiService {

    companion object {
        fun create(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient
            if (BuildConfig.STETHO_ENABLED) {
                client = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(ApiKeyInterceptor())
                    .build()
            }
            else {
                client = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(ApiKeyInterceptor())
                    .build()
            }
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
        }
    }

    @GET(SEARCH_API + API_PRESET_VALUES)
    suspend fun getPhotos(@Query("tags") tag: String) : Response<FetchedData>

}