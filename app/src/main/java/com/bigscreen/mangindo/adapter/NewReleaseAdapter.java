package com.bigscreen.mangindo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.item.MangaViewHolder;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.loader.NewReleaseLoader;
import com.bigscreen.mangindo.network.model.Manga;
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;

import java.util.List;

public class NewReleaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        NewReleaseLoader.OnLoadNewReleaseListener, MangaViewHolder.OnMangaClickListener {

    private Context context;

    private List<Manga> mangaList;
    private NewReleaseLoader newReleaseLoader;
    private OnLoadDataListener loadDataListener;
    private OnListItemClickListener listItemClickListener;

    public NewReleaseAdapter(Context context, OnLoadDataListener loadDataListener, MangaApiService apiService) {
        this.context = context;
        this.loadDataListener = loadDataListener;
        newReleaseLoader = new NewReleaseLoader(apiService, this);
    }

    public void loadManga() {
        newReleaseLoader.loadNewReleaseManga();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manga_cover, parent, false);
        return new MangaViewHolder(view, context, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MangaViewHolder) holder).bindData(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return mangaList == null ? 0 : mangaList.size();
    }

    public Manga getItem(int position) {
        return getItemCount() > 0 ? mangaList.get(position) : null;
    }

    public void setMangaList(List<Manga> mangaList) {
        this.mangaList = mangaList;
        notifyDataSetChanged();
    }

    public List<Manga> getMangaList() {
        return mangaList;
    }

    @Override
    public void onPrepareLoadData() {
        Log.d(Constant.LOG_TAG, "load data...");
        loadDataListener.onPrepare();
    }

    @Override
    public void onSuccessLoadData(NewReleaseResponse newReleaseResponse) {
        Log.d(Constant.LOG_TAG, "success load data\n" + newReleaseResponse.toString());
        loadDataListener.onSuccess();
        setMangaList(newReleaseResponse.getKomik());
    }

    @Override
    public void onFailedLoadData(String message) {
        Log.e(Constant.LOG_TAG, "failed load data, " + message);
        loadDataListener.onError(message);
    }

    public void onParentDestroyed() {
        newReleaseLoader.unSubscribe();
    }

    @Override
    public void onMangaClick(int position) {
        if (listItemClickListener != null)
            listItemClickListener.onListItemClick(position);
    }

    public void setOnListItemClickListener(OnListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }
}
