package com.example.redditapp.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Double?.getHours (): String {
    if (this == null) return ""
    val createdPost = this.toLong() * 1000
    val currentTime = System.currentTimeMillis()
    val diff = (currentTime.minus(createdPost))
    val hour = diff / (1000 * 60 * 60)

    return if (hour < 1) {
        "now"
    } else {
        hour.toString() + "h"
    }
}

fun Context?.isNetworkAvailable(): Boolean {
    if (this == null) return false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}