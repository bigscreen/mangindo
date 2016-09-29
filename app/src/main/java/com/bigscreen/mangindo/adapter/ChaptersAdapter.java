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

import java.util.List;

public class ChaptersAdapter extends BaseAdapter implements ChapterListLoader.OnLoadChapterListListener {

    private Context context;

    private List<Chapter> chapters;
    private ChapterListLoader chapterListLoader;
    private OnLoadDataListener loadDataListener;

    public ChaptersAdapter(Context context, OnLoadDataListener loadDataListener, MangaApiService apiService) {
        this.context = context;
        this.loadDataListener = loadDataListener;
        chapterListLoader = new ChapterListLoader(apiService, this);
    }

    public void loadChapters(String mangaTitle) {
        chapterListLoader.loadChapterList(mangaTitle);
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

    public List<Chapter> getChapters() {
        return chapters;
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
        setChapters(chapterListResponse.getKomik());
    }

    @Override
    public void onFailedLoadData(String message) {
        Log.e(Constant.LOG_TAG, "failed load data, " + message);
        loadDataListener.onError(message);
    }

    public void onParentDestroyed() {
        chapterListLoader.unSubscribe();
    }
}
