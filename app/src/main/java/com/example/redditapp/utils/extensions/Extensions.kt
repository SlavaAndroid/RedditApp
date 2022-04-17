package com.example.redditapp.utils.extensions

import android.webkit.MimeTypeMap




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