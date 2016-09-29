package com.bigscreen.mangindo.network.service;

import com.bigscreen.mangindo.network.model.response.ChapterListResponse;
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse;
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MangaNetworkService {

    @GET("official/new-release.php")
    Observable<NewReleaseResponse> getNewReleaseManga();

    @GET("official/chapter-list.php")
    Observable<ChapterListResponse> getChapters(@Query("manga") String mangaTitle);

    @GET("official/image-list.php")
    Observable<MangaContentListResponse> getMangaContents(@Query("manga") String mangaTitle, @Query("chapter") String chapter);

}
