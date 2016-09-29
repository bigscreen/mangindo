package com.bigscreen.mangindo.listener;

public interface OnLoadDataListener {

    void onPrepare();

    void onSuccess();

    void onError(String errorMessage);

}
