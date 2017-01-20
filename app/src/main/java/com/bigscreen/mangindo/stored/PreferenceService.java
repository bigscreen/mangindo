package com.bigscreen.mangindo.stored;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceService {

    private SharedPreferences sharedPreference;

    public PreferenceService(Context context) {
        sharedPreference = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public String getString(String key, String defValue) {
        return sharedPreference.getString(key, defValue);
    }

    public void saveString(String key, String value) {
        sharedPreference.edit().putString(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreference.getInt(key, defValue);
    }

    public void saveInt(String key, int value) {
        sharedPreference.edit().putInt(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreference.getBoolean(key, defValue);
    }

    public void saveBoolean(String key, boolean value) {
        sharedPreference.edit().putBoolean(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return sharedPreference.getLong(key, defValue);
    }

    public void saveLong(String key, long value) {
        sharedPreference.edit().putLong(key, value).apply();
    }

    public void remove(String key) {
        sharedPreference.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreference.edit().clear().apply();
    }
}
