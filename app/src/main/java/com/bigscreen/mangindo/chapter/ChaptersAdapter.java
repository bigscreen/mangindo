package com.bigscreen.mangindo.chapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.model.Chapter;
import com.bigscreen.mangindo.network.model.response.ChapterListResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;

import java.util.ArrayList;
import java.util.List;

public class ChaptersAdapter extends RecyclerView.Adapter<ChapterViewHolder> implements ChapterListLoader.OnLoadChapterListListener,
        ChapterViewHolder.OnChapterClickListener {

    private Context context;

    private List<Chapter> chapters;
    private ChapterListLoader chapterListLoader;
    private OnLoadDataListener loadDataListener;
    private StoredDataService storedDataService;
    private OnListItemClickListener listItemClickListener;

    private String comicHiddenKey;

    public ChaptersAdapter(Context context, OnLoadDataListener loadDataListener, String comicHiddenKey,
                           StoredDataService storedDataService, MangaApiService apiService) {
        this.context = context;
        this.loadDataListener = loadDataListener;
        this.comicHiddenKey = comicHiddenKey;
        this.storedDataService = storedDataService;
        chapters = new ArrayList<>();
        chapterListLoader = new ChapterListLoader(apiService, this);
    }

    public void loadChapters() {
        chapterListLoader.loadChapterList(comicHiddenKey);
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public Chapter getItem(int position) {
        return getItemCount() > 0 && position < getItemCount() ? chapters.get(position) : null;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters.clear();
        this.chapters.addAll(chapters);
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

    @Override
    public void onChapterClick(int position) {
        if (listItemClickListener != null)
            listItemClickListener.onListItemClick(position);
    }

    public void setOnListItemClickListener(OnListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void onParentDestroyed() {
        chapterListLoader.unSubscribe();
    }
}
