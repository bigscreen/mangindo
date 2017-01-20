package com.bigscreen.mangindo.stored;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StoredDataModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public PreferenceService providesPreferenceService(Context context) {
        return new PreferenceService(context);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public StoredDataService providesStoredDataService(PreferenceService preferenceService) {
        return new StoredDataService(preferenceService);
    }
}
