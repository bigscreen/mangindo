package com.bigscreen.mangindo.manga.chapter;

import com.bigscreen.mangindo.network.NetworkError;
import com.bigscreen.mangindo.network.model.response.ChapterListResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ChapterListLoader {

    private MangaApiService mangaApiService;
    private OnLoadChapterListListener onLoadChapterListListener;
    private CompositeSubscription subscriptions;

    public ChapterListLoader(MangaApiService apiService, OnLoadChapterListListener listener) {
        mangaApiService = apiService;
        onLoadChapterListListener = listener;
        subscriptions = new CompositeSubscription();
    }

    public void loadChapterList(String mangaTitle) {
        onLoadChapterListListener.onPrepareLoadData();
        Subscription subscription = mangaApiService.getChapters(mangaTitle, new MangaApiService.LoadChapterListCallback() {
            @Override
            public void onSuccess(ChapterListResponse response) {
                if (response != null)
                    onLoadChapterListListener.onSuccessLoadData(response);
                else
                    onLoadChapterListListener.onFailedLoadData("Chapter dapat ditemukan");
            }

            @Override
            public void onError(NetworkError networkError) {
                onLoadChapterListListener.onFailedLoadData(networkError.getErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void unSubscribe() {
        subscriptions.unsubscribe();
    }

    public interface OnLoadChapterListListener {
        void onPrepareLoadData();

        void onSuccessLoadData(ChapterListResponse chapterListResponse);

        void onFailedLoadData(String message);
    }
}
