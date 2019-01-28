package com.bigscreen.mangindo.deps

import com.bigscreen.mangindo.network.service.MangaApiService
import com.bigscreen.mangindo.network.service.MangaNetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MangaModule {

    @Provides
    @Singleton
    fun providesMangaNetworkService(retrofit: Retrofit): MangaNetworkService {
        return retrofit.create(MangaNetworkService::class.java)
    }

    @Provides
    @Singleton
    fun providesMangaApiService(mangaNetworkService: MangaNetworkService): MangaApiService {
        return MangaApiService(mangaNetworkService)
    }
}
