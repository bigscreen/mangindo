package com.bigscreen.mangindo.helper;


import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;
import com.google.gson.Gson;

public class AppPreferences {

    private static final String KEY_NEW_RELEASE = "newReleasedManga";

    private PrefHelper prefHelper;
    private Gson gson;


    public AppPreferences(PrefHelper prefHelper) {
        this.prefHelper = prefHelper;
        gson = new Gson();
    }

    public void saveNewReleasedManga(NewReleaseResponse newReleaseResponse) {
        String responseString = gson.toJson(newReleaseResponse, NewReleaseResponse.class);

    }
}
