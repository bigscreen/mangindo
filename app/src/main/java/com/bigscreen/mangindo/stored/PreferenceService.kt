package com.bigscreen.mangindo.stored

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PreferenceService(context: Context) {

    private val sharedPreference by lazy {
        context.getSharedPreferences(context.packageName, MODE_PRIVATE)
    }

    fun getString(key: String, defValue: String): String? {
        return sharedPreference.getString(key, defValue)
    }

    fun saveString(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return sharedPreference.getInt(key, defValue)
    }

    fun saveInt(key: String, value: Int) {
        sharedPreference.edit().putInt(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        return sharedPreference.getLong(key, defValue)
    }

    fun saveLong(key: String, value: Long) {
        sharedPreference.edit().putLong(key, value).apply()
    }

    fun remove(key: String) {
        sharedPreference.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreference.edit().clear().apply()
    }
}