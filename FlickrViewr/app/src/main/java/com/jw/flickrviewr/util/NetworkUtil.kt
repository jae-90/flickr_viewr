package com.jw.flickrviewr.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED

object NetworkUtil {

    /**
     * Returns true if the network is connected.
     */
    fun isInternetConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetwork
        val networkCapabilities = cm.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED) &&
                networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)
    }

}