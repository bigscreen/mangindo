package com.bigscreen.mangindo.helper;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private SharedPreferences sharedPreference;

    public PrefHelper(Context context) {
        sharedPreference = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public String getString(String key, String defValue) {
        return sharedPreference.getString(key, defValue);
    }

    public String setString(String key, String value) {
        sharedPreference.edit().putString(key, value).apply();
        return key;
    }

    public int getInt(String key, int defValue) {
        return sharedPreference.getInt(key, defValue);
    }

    public void setInt(String key, int value) {
        sharedPreference.edit().putInt(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreference.getBoolean(key, defValue);
    }

    public void setBoolean(String key, boolean value) {
        sharedPreference.edit().putBoolean(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return sharedPreference.getLong(key, defValue);
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void remove(String key) {
        sharedPreference.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreference.edit().clear().apply();
    }
}
