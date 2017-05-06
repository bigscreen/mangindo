package com.bigscreen.mangindo.newrelease;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.stored.StoredDataService;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.model.Manga;
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewReleaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        NewReleaseLoader.OnLoadNewReleaseListener, NewReleaseViewHolder.OnMangaClickListener {

    public static final int SORT_BY_DATE = 1;
    public static final int SORT_BY_TITLE = 2;

    private Context context;

    private List<Manga> mangaList, backupMangaList;
    private NewReleaseLoader newReleaseLoader;
    private OnLoadDataListener loadDataListener;
    private OnListItemClickListener listItemClickListener;
    private StoredDataService storedDataService;

    private int sortBy = SORT_BY_DATE;
    private String searchKeyword = "";

    public NewReleaseAdapter(Context context, OnLoadDataListener loadDataListener,
                             StoredDataService storedDataService, MangaApiService apiService) {
        this.context = context;
        this.loadDataListener = loadDataListener;
        this.storedDataService = storedDataService;
        newReleaseLoader = new NewReleaseLoader(apiService, this);
    }

    public void loadManga() {
        newReleaseLoader.loadNewReleaseManga();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_release, parent, false);
        return new NewReleaseViewHolder(view, context, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (searchKeyword.isEmpty())
            ((NewReleaseViewHolder) holder).bindData(getItem(position));
        else
            ((NewReleaseViewHolder) holder).bindDataSearch(getItem(position), searchKeyword);
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

    @Override
    public void onPrepareLoadData() {
        Log.d(Constant.LOG_TAG, "load data...");
        loadDataListener.onPrepare();
    }

    @Override
    public void onSuccessLoadData(NewReleaseResponse newReleaseResponse) {
        Log.d(Constant.LOG_TAG, "success load data\n" + newReleaseResponse.toString());
        loadDataListener.onSuccess();
        setMangaList(newReleaseResponse.getComics());
        storedDataService.saveNewReleasedComic(newReleaseResponse);
    }

    @Override
    public void onFailedLoadData(String message) {
        Log.e(Constant.LOG_TAG, "failed load data, " + message);
        loadDataFromPreference(message);
    }

    private void loadDataFromPreference(final String networkErrorMessage) {
        storedDataService.pullStoredNewReleasedComic(new StoredDataService.OnGetSavedDataListener<NewReleaseResponse>() {
            @Override
            public void onDataFound(NewReleaseResponse savedData) {
                loadDataListener.onSuccess();
                setMangaList(savedData.getComics());
            }

            @Override
            public void onDataNotFound() {
                loadDataListener.onError(networkErrorMessage);
            }
        });
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
