package com.bigscreen.mangindo.repos

import com.bigscreen.mangindo.common.extension.configured
import com.bigscreen.mangindo.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(private val apiService: ApiService) {

    fun getNewReleased() = apiService.getNewReleased().configured()

    fun getChapters(title: String) = apiService.getChapters(title).configured()

    fun getContents(title: String, chapter: String) = apiService.getContents(title, chapter).configured()
}
