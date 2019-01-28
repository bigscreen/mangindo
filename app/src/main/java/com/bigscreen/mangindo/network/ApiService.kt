package com.bigscreen.mangindo.network

import com.bigscreen.mangindo.network.model.response.ChapterListResponse
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApiService {

    @GET("main.php")
    fun getNewReleased(): Observable<NewReleaseResponse>

    @GET("chapter_list.php")
    fun getChapters(
            @Query("manga") title: String
    ): Observable<ChapterListResponse>

    @GET("image_list.php")
    fun getContents(
            @Query("manga") title: String,
            @Query("chapter") chapter: String
    ): Observable<MangaContentListResponse>
}
