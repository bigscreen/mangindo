package com.bigscreen.mangindo.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.bigscreen.mangindo.deps.AppDeps
import com.bigscreen.mangindo.deps.AppModule
import com.bigscreen.mangindo.deps.DaggerAppDeps

class MangindoApplication : Application() {

    private lateinit var appDepsX: AppDeps

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        appDepsX = DaggerAppDeps.builder().appModule(AppModule(this)).build()
    }

    fun getAppDeps(): AppDeps {
        return appDepsX
    }
}
