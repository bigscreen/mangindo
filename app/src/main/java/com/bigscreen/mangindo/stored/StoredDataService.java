package com.bigscreen.mangindo.stored;


import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;
import com.google.gson.Gson;

import static com.bigscreen.mangindo.common.Utils.isTextNotNullOrEmpty;

public class StoredDataService {

    private static final String KEY_NEW_RELEASE = "COMIC_NEW_RELEASE";

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
        if (isTextNotNullOrEmpty(responseString)) {
            callback.onDataFound(gson.fromJson(responseString, NewReleaseResponse.class));
        } else {
            callback.onDataNotFound();
        }
    }

    public interface OnGetSavedDataListener<T> {
        void onDataFound(T savedData);
        void onDataNotFound();
    }
}
