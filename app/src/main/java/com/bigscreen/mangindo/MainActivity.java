package com.bigscreen.mangindo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigscreen.mangindo.adapter.NewReleaseAdapter;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.service.MangaApiService;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnLoadDataListener, OnListItemClickListener {

    private RecyclerView gridMangaNewRelease;
    private ProgressBar progressLoading;
    private NewReleaseAdapter newReleaseAdapter;

    @Inject
    MangaApiService mangaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppDeps().inject(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        gridMangaNewRelease = (RecyclerView) findViewById(R.id.grid_manga_new_release);
        progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
        newReleaseAdapter = new NewReleaseAdapter(this, this, mangaApiService);

        gridMangaNewRelease.setLayoutManager(new GridLayoutManager(this, 3));
        gridMangaNewRelease.setAdapter(newReleaseAdapter);

        newReleaseAdapter.setOnListItemClickListener(this);
        newReleaseAdapter.loadManga();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textTitle = (TextView) findViewById(R.id.text_main_title);
        textTitle.setText(title);
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
    }

    @Override
    protected void onDestroy() {
        newReleaseAdapter.onParentDestroyed();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, ChapterListActivity.class);
        intent.putExtra(IntentKey.MANGA_KEY, newReleaseAdapter.getItem(position).getHiddenKomik());
        intent.putExtra(IntentKey.MANGA_TITLE, newReleaseAdapter.getItem(position).getJudul());
        startActivity(intent);
    }
}
