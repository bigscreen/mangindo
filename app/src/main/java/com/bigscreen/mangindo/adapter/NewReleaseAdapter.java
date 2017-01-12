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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewReleaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        NewReleaseLoader.OnLoadNewReleaseListener, MangaViewHolder.OnMangaClickListener {

    public static final int SORT_BY_DATE = 1;
    public static final int SORT_BY_TITLE = 2;

    private Context context;

    private List<Manga> mangaList, backupMangaList;
    private NewReleaseLoader newReleaseLoader;
    private OnLoadDataListener loadDataListener;
    private OnListItemClickListener listItemClickListener;

    private int sortBy = SORT_BY_DATE;
    private String searchKeyword = "";

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
        if (searchKeyword.isEmpty())
            ((MangaViewHolder) holder).bindData(getItem(position), position);
        else
            ((MangaViewHolder) holder).bindDataSearch(getItem(position), position, searchKeyword);
    }

    @Override
    public int getItemCount() {
        return mangaList == null ? 0 : mangaList.size();
    }

    public Manga getItem(int position) {
        return getItemCount() > 0 ? mangaList.get(position) : null;
    }

    private void setMangaList(List<Manga> mangaList) {
        this.mangaList = mangaList;
        backupMangaList = mangaList;
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

    public void setSortingOption(int sortBy) {
        if (this.sortBy == sortBy) return;
        this.sortBy = sortBy;
        switch (sortBy) {
            case SORT_BY_DATE:
                sortMangaListByDate();
                break;
            case SORT_BY_TITLE:
                sortMangaListByName();
                break;
            default:
                break;
        }
    }

    private void sortMangaListByDate() {
        Collections.sort(mangaList, new Comparator<Manga>() {
            @Override
            public int compare(Manga manga, Manga mangaComparator) {
                int id = manga.getId();
                int idComparator = mangaComparator.getId();
                return id - idComparator;
            }
        });
        notifyDataSetChanged();
    }

    private void sortMangaListByName() {
        Collections.sort(mangaList, new Comparator<Manga>() {
            @Override
            public int compare(Manga manga, Manga mangaComparator) {
                String title = manga.getTitle().toLowerCase();
                String titleComparator = mangaComparator.getTitle().toLowerCase();
                return title.compareTo(titleComparator);
            }
        });
        notifyDataSetChanged();
    }

    public void filterList(String keyword) {
        searchKeyword = keyword;
        mangaList = getBackupMangaList();
        if (!keyword.isEmpty()) {
            List<Manga> filteredList = new ArrayList<>();
            for (Manga manga : mangaList) {
                if (isStartWith(manga.getTitle(), keyword) || isStartWith(manga.getHiddenComic(), keyword)) {
                    filteredList.add(manga);
                }
            }
            mangaList = filteredList;
        }
        notifyDataSetChanged();
    }

    private boolean isStartWith(String firstArg, String secondArg) {
        return firstArg.toLowerCase().startsWith(secondArg.toLowerCase());
    }

    private List<Manga> getBackupMangaList() {
        List<Manga> tempMangaList = backupMangaList;
        switch (sortBy) {
            case SORT_BY_DATE:
                Collections.sort(tempMangaList, new Comparator<Manga>() {
                    @Override
                    public int compare(Manga manga, Manga mangaComparator) {
                        int id = manga.getId();
                        int idComparator = mangaComparator.getId();
                        return id - idComparator;
                    }
                });
                break;
            case SORT_BY_TITLE:
                Collections.sort(tempMangaList, new Comparator<Manga>() {
                    @Override
                    public int compare(Manga manga, Manga mangaComparator) {
                        String title = manga.getTitle().toLowerCase();
                        String titleComparator = mangaComparator.getTitle().toLowerCase();
                        return title.compareTo(titleComparator);
                    }
                });
                break;
            default:
                break;
        }
        return tempMangaList;
    }

}
