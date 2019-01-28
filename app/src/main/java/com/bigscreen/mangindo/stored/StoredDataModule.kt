package com.bigscreen.mangindo.stored

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StoredDataModule {

    @Provides
    @Singleton
    fun providesPreferenceService(context: Context): PreferenceService {
        return PreferenceService(context)
    }

    @Provides
    @Singleton
    fun providesStoredDataService(preferenceService: PreferenceService): StoredDataService {
        return StoredDataService(preferenceService)
    }
}
