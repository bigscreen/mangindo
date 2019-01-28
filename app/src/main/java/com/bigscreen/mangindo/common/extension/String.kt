package com.bigscreen.mangindo.common.extension

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

fun String.getEncodedUrl() = try {
    URLEncoder.encode(this, "UTF-8")
} catch (e: UnsupportedEncodingException) {
    e.printStackTrace()
    this
}

fun String.isNotAdsUrl(): Boolean {
    val adsKeywords = arrayOf("iklan", "all_anime", "ik.jpg", "rekrut", "ilan.jpg", "animeindonesia",
            "IKLAN2", "Credit", "animeindonesia")
    for (keyword in adsKeywords) {
        if (contains(keyword)) return false
    }
    return true
}

fun String?.isNotNullOrEmpty(): Boolean {
    return this.isNullOrEmpty().not()
}