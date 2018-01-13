package com.bigscreen.mangindo.deps;

import com.bigscreen.mangindo.chapter.ChapterListActivity;
import com.bigscreen.mangindo.content.MangaContentActivity;
import com.bigscreen.mangindo.network.NetworkModule;
import com.bigscreen.mangindo.newrelease.NewReleaseActivity;
import com.bigscreen.mangindo.stored.StoredDataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        StoredDataModule.class,
        NetworkModule.class,
        MangaModule.class
})
public interface AppDeps {

    void inject(NewReleaseActivity newReleaseActivity);

    void inject(ChapterListActivity chapterListActivity);

    void inject(MangaContentActivity mangaContentActivity);

}
