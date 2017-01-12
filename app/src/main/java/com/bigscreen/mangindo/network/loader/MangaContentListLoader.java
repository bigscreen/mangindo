package com.bigscreen.mangindo.network.loader;

import com.bigscreen.mangindo.network.NetworkError;
import com.bigscreen.mangindo.network.model.MangaImage;
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MangaContentListLoader {

    private MangaApiService mangaApiService;
    private OnLoadMangaContentListListener onLoadMangaContentListListener;
    private CompositeSubscription subscriptions;

    public MangaContentListLoader(MangaApiService apiService, OnLoadMangaContentListListener listener) {
        mangaApiService = apiService;
        onLoadMangaContentListListener = listener;
        subscriptions = new CompositeSubscription();
    }

    public void loadContentList(String mangaTitle, String chapter) {
        onLoadMangaContentListListener.onPrepareLoadData();
        Subscription subscription = mangaApiService.getMangaContent(mangaTitle, chapter, new MangaApiService.LoadMangaContentListCallback() {
            @Override
            public void onSuccess(MangaContentListResponse response) {
                if (response != null)
                    onLoadMangaContentListListener.onSuccessLoadData(getNonAdsMangaContent(response));
                else
                    onLoadMangaContentListListener.onFailedLoadData("Cannot load chapter");
            }

            @Override
            public void onError(NetworkError networkError) {
                onLoadMangaContentListListener.onFailedLoadData(networkError.getErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void unSubscribe() {
        subscriptions.unsubscribe();
    }

    public interface OnLoadMangaContentListListener {
        void onPrepareLoadData();

        void onSuccessLoadData(MangaContentListResponse mangaContentListResponse);

        void onFailedLoadData(String message);
    }

    private MangaContentListResponse getNonAdsMangaContent(MangaContentListResponse mangaContent) {
        List<MangaImage> mangaImages = mangaContent.getChapter();
        List<MangaImage> newMangaImages = new ArrayList<>();
        for (MangaImage mangaImage : mangaImages) {
            if (!mangaImage.getUrl().contains("iklan") && !mangaImage.getUrl().contains("all_anime")
                    && !mangaImage.getUrl().contains("ik.jpg") && !mangaImage.getUrl().contains("rekrut")
                    && !mangaImage.getUrl().contains("ilan.jpg") && !mangaImage.getUrl().contains("animeindonesia"))
                newMangaImages.add(mangaImage);
        }
        mangaContent.setChapter(newMangaImages);
        return mangaContent;
    }

}
