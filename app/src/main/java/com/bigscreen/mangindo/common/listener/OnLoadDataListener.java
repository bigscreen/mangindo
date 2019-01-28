package com.bigscreen.mangindo.common.listener;

public interface OnLoadDataListener {

    void onPrepare();

    void onSuccess();

    void onError(String errorMessage);

}
