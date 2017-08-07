package com.bigscreen.mangindo.info

import com.bigscreen.mangindo.network.model.Manga


class MangaInfoViewModel(var manga: Manga?) {

    val authorName: String
        get() = manga?.author ?: "N/A"

    val status: String
        get() = manga?.status ?: "N/A"

    val genre: String
        get() = manga?.genre ?: "N/A"

    val summary: String
        get() = manga?.summary ?: "N/A"
}