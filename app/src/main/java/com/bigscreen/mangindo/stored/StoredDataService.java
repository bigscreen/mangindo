package com.bigscreen.mangindo.stored;

import com.bigscreen.mangindo.common.extension.StringKt;
import com.bigscreen.mangindo.network.model.response.ChapterListResponse;
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse;
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;
import com.google.gson.Gson;

public class StoredDataService {

    private static final String KEY_NEW_RELEASE = "COMIC_NEW_RELEASE";
    private static final String KEY_CHAPTER_FORMAT = "COMIC_CHAPTER_%s";
    private static final String KEY_CONTENT_FORMAT = "COMIC_CONTENT_%s_%s";

    private PreferenceService preferenceService;
    private Gson gson;


    public StoredDataService(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
        gson = new Gson();
    }

    public void saveNewReleasedComic(NewReleaseResponse newReleaseResponse) {
        String responseString = gson.toJson(newReleaseResponse, NewReleaseResponse.class);
        preferenceService.saveString(KEY_NEW_RELEASE, responseString);
    }

    public void pullStoredNewReleasedComic(OnGetSavedDataListener<NewReleaseResponse> callback) {
        String responseString = preferenceService.getString(KEY_NEW_RELEASE, null);
        if (StringKt.isNotNullOrEmpty(responseString)) {
            callback.onDataFound(gson.fromJson(responseString, NewReleaseResponse.class));
        } else {
            callback.onDataNotFound();
        }
    }

    public void saveChapterOfComic(String comicHiddenKey, ChapterListResponse chapterListResponse) {
        String responseString = gson.toJson(chapterListResponse, ChapterListResponse.class);
        String formattedKey = String.format(KEY_CHAPTER_FORMAT, comicHiddenKey);
        preferenceService.saveString(formattedKey, responseString);
    }

    public void pullStoredChapterOfComic(String comicHiddenKey, OnGetSavedDataListener<ChapterListResponse> callback) {
        String formattedKey = String.format(KEY_CHAPTER_FORMAT, comicHiddenKey);
        String responseString = preferenceService.getString(formattedKey, null);
        if (StringKt.isNotNullOrEmpty(responseString)) {
            callback.onDataFound(gson.fromJson(responseString, ChapterListResponse.class));
        } else {
            callback.onDataNotFound();
        }
    }

    public void saveContentOfComic(String comicHiddenKey, String chapter, MangaContentListResponse mangaContentListResponse) {
        String responseString = gson.toJson(mangaContentListResponse, MangaContentListResponse.class);
        String formattedKey = String.format(KEY_CONTENT_FORMAT, comicHiddenKey, chapter);
        preferenceService.saveString(formattedKey, responseString);
    }

    public void pullStoredContentOfComic(String comicHiddenKey, String chapter,
                                         OnGetSavedDataListener<MangaContentListResponse> callback) {
        String formattedKey = String.format(KEY_CONTENT_FORMAT, comicHiddenKey, chapter);
        String responseString = preferenceService.getString(formattedKey, null);
        if (StringKt.isNotNullOrEmpty(responseString)) {
            callback.onDataFound(gson.fromJson(responseString, MangaContentListResponse.class));
        } else {
            callback.onDataNotFound();
        }
    }

    public interface OnGetSavedDataListener<T> {
        void onDataFound(T savedData);
        void onDataNotFound();
    }
}
