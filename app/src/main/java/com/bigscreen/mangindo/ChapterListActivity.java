package com.bigscreen.mangindo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bigscreen.mangindo.adapter.ChaptersAdapter;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

public class ChapterListActivity extends BaseActivity implements OnLoadDataListener, OnListItemClickListener {

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView imageMangaCover;
    private RecyclerView listChapters;
    private ProgressBar progressLoading;
    private ChaptersAdapter chaptersAdapter;

    private String mangaKey;
    private String mangaCompleteTitle;
    private String mangaCoverUrl;

    @Inject
    StoredDataService storedDataService;

    @Inject
    MangaApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppDeps().inject(this);
        setContentView(R.layout.activity_chapter_list);

        mangaKey = getIntent().getStringExtra(IntentKey.MANGA_KEY);
        mangaCompleteTitle = getIntent().getStringExtra(IntentKey.MANGA_TITLE);
        mangaCoverUrl = getIntent().getStringExtra(IntentKey.MANGA_COVER_URL);
        if (mangaKey == null || mangaCompleteTitle == null) {
            Log.e(Constant.LOG_TAG, "Could not found intent extra");
            finish();
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_collapsing);
        imageMangaCover = (ImageView) findViewById(R.id.image_manga_cover);
        listChapters = (RecyclerView) findViewById(R.id.list_chapters);
        progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
        chaptersAdapter = new ChaptersAdapter(this, this, mangaKey, storedDataService, apiService);
        setCollapsingToolbarContent();
        initRecyclerView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mangaCompleteTitle);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        chaptersAdapter.loadChapters();
    }

    private void setCollapsingToolbarContent() {
        collapsingToolbar.setTitle(mangaCompleteTitle);
        imageMangaCover.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryOverlay_50));
        Glide.with(this).load(mangaCoverUrl)
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        intent.putExtra(IntentKey.MANGA_KEY, mangaKey);
        intent.putExtra(IntentKey.MANGA_TITLE, mangaCompleteTitle);
        intent.putExtra(IntentKey.CHAPTER_KEY, chaptersAdapter.getItem(position).getHiddenChapter());
        startActivity(intent);
    }
}
