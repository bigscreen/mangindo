package com.bigscreen.mangindo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bigscreen.mangindo.adapter.ChaptersAdapter;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.manga.info.MangaInfoActivity;
import com.bigscreen.mangindo.network.model.Manga;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

public class ChapterListActivity extends BaseActivity implements OnLoadDataListener, OnListItemClickListener,
        View.OnClickListener {

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView imageMangaCover;
    private FloatingActionButton buttonInfo;
    private RecyclerView listChapters;
    private ProgressBar progressLoading;
    private ChaptersAdapter chaptersAdapter;

    private Manga manga;

    @Inject
    StoredDataService storedDataService;

    @Inject
    MangaApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppDeps().inject(this);
        setContentView(R.layout.activity_chapter_list);

        manga = getIntent().getParcelableExtra(IntentKey.MANGA_DATA);
        if (manga == null) {
            Log.e(Constant.LOG_TAG, "Could not found intent extra");
            finish();
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_collapsing);
        imageMangaCover = (ImageView) findViewById(R.id.image_manga_cover);
        buttonInfo = (FloatingActionButton) findViewById(R.id.button_info);
        listChapters = (RecyclerView) findViewById(R.id.list_chapters);
        progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
        chaptersAdapter = new ChaptersAdapter(this, this, manga.getHiddenComic(), storedDataService, apiService);

        setCollapsingToolbarContent();
        initRecyclerView();
        setToolbarTitle(manga.getTitle(), true);
        chaptersAdapter.loadChapters();
    }

    private void setCollapsingToolbarContent() {
        collapsingToolbar.setTitle(manga.getTitle());
        imageMangaCover.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryOverlay_50));
        buttonInfo.setOnClickListener(this);
        Glide.with(this).load(manga.getComicIcon())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageMangaCover);
    }

    private void initRecyclerView() {
        listChapters.setHasFixedSize(true);
        listChapters.setLayoutManager(new LinearLayoutManager(this));
        listChapters.setAdapter(chaptersAdapter);
        chaptersAdapter.setOnListItemClickListener(this);
    }

    @Override
    public void onPrepare() {
        progressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorMessage) {
        progressLoading.setVisibility(View.GONE);
        showAlert("Error", errorMessage, "Reload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chaptersAdapter.loadChapters();
            }
        });
    }

    @Override
    protected void onDestroy() {
        chaptersAdapter.onParentDestroyed();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(ChapterListActivity.this, MangaContentActivity.class);
        intent.putExtra(IntentKey.MANGA_KEY, manga.getHiddenComic());
        intent.putExtra(IntentKey.MANGA_TITLE, manga.getTitle());
        intent.putExtra(IntentKey.CHAPTER_KEY, chaptersAdapter.getItem(position).getHiddenChapter());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_info:
                Intent intent = new Intent(ChapterListActivity.this, MangaInfoActivity.class);
                intent.putExtra(IntentKey.MANGA_DATA, manga);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
