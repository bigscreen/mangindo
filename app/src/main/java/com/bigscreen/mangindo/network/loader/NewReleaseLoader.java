package com.bigscreen.mangindo.network.loader;

import com.bigscreen.mangindo.network.NetworkError;
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class NewReleaseLoader {

    private MangaApiService mangaApiService;
    private OnLoadNewReleaseListener loadNewReleaseListener;
    private CompositeSubscription subscriptions;

    public NewReleaseLoader(MangaApiService apiService, OnLoadNewReleaseListener listener) {
        mangaApiService = apiService;
        loadNewReleaseListener = listener;
        subscriptions = new CompositeSubscription();
    }

    public void loadNewReleaseManga() {
        loadNewReleaseListener.onPrepareLoadData();
        Subscription subscription = mangaApiService.getNewReleaseManga(new MangaApiService.LoadMangaListCallback() {
            @Override
            public void onSuccess(NewReleaseResponse response) {
                if (response != null)
                    loadNewReleaseListener.onSuccessLoadData(response);
                else
                    loadNewReleaseListener.onFailedLoadData("Manga tidak dapat ditemukan");
            }

            @Override
            public void onError(NetworkError networkError) {
                loadNewReleaseListener.onFailedLoadData(networkError.getErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void unSubscribe() {
        subscriptions.unsubscribe();
    }

    public interface OnLoadNewReleaseListener {
        void onPrepareLoadData();

        void onSuccessLoadData(NewReleaseResponse newReleaseResponse);

        void onFailedLoadData(String message);
    }
}
