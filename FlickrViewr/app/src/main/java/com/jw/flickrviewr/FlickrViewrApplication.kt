package com.jw.flickrviewr

import android.app.Application
import com.facebook.stetho.Stetho

class FlickrViewrApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.STETHO_ENABLED) {
            Stetho.initializeWithDefaults(this)
        }
    }
}