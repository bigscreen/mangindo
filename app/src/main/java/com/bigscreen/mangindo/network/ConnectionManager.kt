package com.bigscreen.mangindo.network

import android.content.Context
import android.net.ConnectivityManager

class ConnectionManager(private val context: Context) {

    companion object {
        const val TYPE_WIFI = 1
        const val TYPE_MOBILE = 2
        const val TYPE_NOT_CONNECTED = 0
    }

    fun getConnectivityStatus(): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected && activeNetwork.isAvailable) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI
            }
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE
            }
        }
        return TYPE_NOT_CONNECTED
    }

    fun isConnected(): Boolean {
        return getConnectivityStatus() != TYPE_NOT_CONNECTED
    }
}
