package com.bigscreen.mangindo.deps

import com.bigscreen.mangindo.chapter.ChapterListActivity
import com.bigscreen.mangindo.content.MangaContentActivity
import com.bigscreen.mangindo.network.NetworkModule
import com.bigscreen.mangindo.newrelease.NewReleaseActivity
import com.bigscreen.mangindo.stored.StoredDataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    StoredDataModule::class,
    NetworkModule::class,
    MangaModule::class
])
interface AppDeps{

    fun inject(newReleaseActivity: NewReleaseActivity)

    fun inject(chapterListActivity: ChapterListActivity)

    fun inject(mangaContentActivity: MangaContentActivity)
}
