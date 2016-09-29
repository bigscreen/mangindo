package com.bigscreen.mangindo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bigscreen.mangindo.adapter.ChaptersAdapter;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.service.MangaApiService;

import javax.inject.Inject;

public class ChapterListActivity extends BaseActivity implements OnLoadDataListener, AdapterView.OnItemClickListener {

    private ListView listChapters;
    private ProgressBar progressLoading;
    private ChaptersAdapter chaptersAdapter;

    private String mangaKey;
    private String mangaCompleteTitle;

    @Inject
    MangaApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppDeps().inject(this);
        setContentView(R.layout.activity_chapter_list);

        mangaKey = getIntent().getStringExtra(IntentKey.MANGA_KEY);
        mangaCompleteTitle = getIntent().getStringExtra(IntentKey.MANGA_TITLE);
        if (mangaKey == null || mangaCompleteTitle == null) {
            Log.e(Constant.LOG_TAG, "Could not found intent extra");
            finish();
        }

        listChapters = (ListView) findViewById(R.id.list_chapters);
        progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
        chaptersAdapter = new ChaptersAdapter(this, this, apiService);
        listChapters.setAdapter(chaptersAdapter);
        listChapters.setOnItemClickListener(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mangaCompleteTitle);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        chaptersAdapter.loadChapters(mangaKey);
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
    }

    @Override
    protected void onDestroy() {
        chaptersAdapter.onParentDestroyed();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
        Intent intent = new Intent(ChapterListActivity.this, MangaContentActivity.class);
        intent.putExtra(IntentKey.MANGA_KEY, mangaKey);
        intent.putExtra(IntentKey.MANGA_TITLE, mangaCompleteTitle);
        intent.putExtra(IntentKey.CHAPTER_KEY, chaptersAdapter.getItem(position).getHiddenChapter());
        startActivity(intent);
    }
}
