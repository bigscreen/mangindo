package com.bigscreen.mangindo.network.module;

import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.network.service.MangaNetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MangaModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public MangaNetworkService providesMangaNetworkService(Retrofit retrofit) {
        return retrofit.create(MangaNetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public MangaApiService providesMangaApiService(MangaNetworkService mangaNetworkService) {
        return new MangaApiService(mangaNetworkService);
    }
}
