package com.bigscreen.mangindo.deps;

import com.bigscreen.mangindo.manga.chapter.ChapterListActivity;
import com.bigscreen.mangindo.MainActivity;
import com.bigscreen.mangindo.manga.content.MangaContentActivity;
import com.bigscreen.mangindo.network.NetworkModule;
import com.bigscreen.mangindo.network.module.MangaModule;
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

    void inject(MainActivity mainActivity);

    void inject(ChapterListActivity chapterListActivity);

    void inject(MangaContentActivity mangaContentActivity);

}
