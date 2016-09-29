package com.bigscreen.mangindo.deps;

import com.bigscreen.mangindo.ChapterListActivity;
import com.bigscreen.mangindo.MainActivity;
import com.bigscreen.mangindo.MangaContentActivity;
import com.bigscreen.mangindo.network.NetworkModule;
import com.bigscreen.mangindo.network.module.MangaModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NetworkModule.class,
        MangaModule.class
})
public interface AppDeps {

    void inject(MainActivity mainActivity);

    void inject(ChapterListActivity chapterListActivity);

    void inject(MangaContentActivity mangaContentActivity);

}
