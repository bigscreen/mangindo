package com.bigscreen.mangindo.network.service;

import android.support.annotation.NonNull;

import com.bigscreen.mangindo.network.NetworkError;
import com.bigscreen.mangindo.network.model.response.ChapterListResponse;
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse;
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MangaApiService {

    private final MangaNetworkService mangaNetworkService;

    public MangaApiService(MangaNetworkService mangaNetworkService) {
        this.mangaNetworkService = mangaNetworkService;
    }

    public Subscription getNewReleaseManga(final LoadMangaListCallback callback) {
        return mangaNetworkService.getNewReleaseManga()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends NewReleaseResponse>>() {
                    @Override
                    public Observable<? extends NewReleaseResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<NewReleaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(NewReleaseResponse newReleaseResponse) {
                        callback.onSuccess(newReleaseResponse);
                    }
                });
    }

    public Subscription getChapters(String mangaTitle, final LoadChapterListCallback callback) {
        return mangaNetworkService.getChapters(mangaTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ChapterListResponse>>() {
                    @Override
                    public Observable<? extends ChapterListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ChapterListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(ChapterListResponse chapterListResponse) {
                        callback.onSuccess(chapterListResponse);
                    }
                });
    }

    public Subscription getMangaContent(String mangaTitle, String chapter, final LoadMangaContentListCallback callback) {
        return mangaNetworkService.getMangaContents(mangaTitle, chapter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MangaContentListResponse>>() {
                    @Override
                    public Observable<? extends MangaContentListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MangaContentListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(MangaContentListResponse mangaContentListResponse) {
                        callback.onSuccess(mangaContentListResponse);
                    }
                });
    }

    public interface LoadMangaListCallback {
        void onSuccess(NewReleaseResponse response);

        void onError(NetworkError networkError);
    }

    public interface LoadChapterListCallback {
        void onSuccess(ChapterListResponse response);

        void onError(NetworkError networkError);
    }

    public interface LoadMangaContentListCallback {
        void onSuccess(MangaContentListResponse response);

        void onError(@NonNull NetworkError networkError);
    }

}
