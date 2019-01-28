package com.bigscreen.mangindo.network.service

import com.bigscreen.mangindo.network.model.response.ChapterListResponse
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface MangaNetworkService {

    @GET("main.php")
    fun getNewReleaseManga(): Observable<NewReleaseResponse>

    @GET("chapter_list.php")
    fun getChapters(
            @Query("manga") mangaTitle: String
    ): Observable<ChapterListResponse>

    @GET("image_list.php")
    fun getMangaContents(
            @Query("manga") mangaTitle: String,
            @Query("chapter") chapter: String
    ): Observable<MangaContentListResponse>
}
