package com.bigscreen.mangindo.base;

import android.app.Application;
import android.util.Log;

import com.bigscreen.mangindo.deps.AppDeps;
import com.bigscreen.mangindo.deps.AppModule;
import com.bigscreen.mangindo.deps.DaggerAppDeps;

public class MangindoApplication extends Application {

    private static final String TAG = MangindoApplication.class.getSimpleName();

    private AppDeps appDeps;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application created.");
        appDeps = DaggerAppDeps.builder().appModule(new AppModule(this)).build();
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "Application terminated.");
        super.onTerminate();
    }

    public AppDeps getAppDeps() {
        return appDeps;
    }
}
