package com.bigscreen.mangindo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.item.ChapterItemView;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.loader.ChapterListLoader;
import com.bigscreen.mangindo.network.model.Chapter;
import com.bigscreen.mangindo.network.model.response.ChapterListResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;

import java.util.List;

public class ChaptersAdapter extends BaseAdapter implements ChapterListLoader.OnLoadChapterListListener {

    private Context context;

    private List<Chapter> chapters;
    private ChapterListLoader chapterListLoader;
    private OnLoadDataListener loadDataListener;
    private StoredDataService storedDataService;

    private String comicHiddenKey;

    public ChaptersAdapter(Context context, OnLoadDataListener loadDataListener, String comicHiddenKey,
                           StoredDataService storedDataService, MangaApiService apiService) {
        this.context = context;
        this.loadDataListener = loadDataListener;
        this.comicHiddenKey = comicHiddenKey;
        this.storedDataService = storedDataService;
        chapterListLoader = new ChapterListLoader(apiService, this);
    }

    public void loadChapters() {
        chapterListLoader.loadChapterList(comicHiddenKey);
    }

    @Override
    public int getCount() {
        return chapters == null ? 0 : chapters.size();
    }

    @Override
    public Chapter getItem(int position) {
        return getCount() > 0 ? chapters.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChapterItemView itemView;
        if (convertView == null) {
            itemView = new ChapterItemView(context);
        } else {
            itemView = (ChapterItemView) convertView;
        }
        itemView.bindData(getItem(position));
        return itemView;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @Override
    public void onPrepareLoadData() {
        Log.d(Constant.LOG_TAG, "load data...");
        loadDataListener.onPrepare();
    }

    @Override
    public void onSuccessLoadData(ChapterListResponse chapterListResponse) {
        Log.d(Constant.LOG_TAG, "success load data\n" + chapterListResponse.toString());
        loadDataListener.onSuccess();
        setChapters(chapterListResponse.getComics());
        storedDataService.saveChapterOfComic(comicHiddenKey, chapterListResponse);
    }

    @Override
    public void onFailedLoadData(String message) {
        Log.e(Constant.LOG_TAG, "failed load data, " + message);
        loadDataFromStoredService(message);
    }

    private void loadDataFromStoredService(final String networkErrorMessage) {
        storedDataService.pullStoredChapterOfComic(comicHiddenKey, new StoredDataService.OnGetSavedDataListener<ChapterListResponse>() {
            @Override
            public void onDataFound(ChapterListResponse savedData) {
                loadDataListener.onSuccess();
                setChapters(savedData.getComics());
            }

            @Override
            public void onDataNotFound() {
                loadDataListener.onError(networkErrorMessage);
            }
        });
    }

    public void onParentDestroyed() {
        chapterListLoader.unSubscribe();
    }
}
