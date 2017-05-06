package com.bigscreen.mangindo.chapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.content.MangaContentActivity;
import com.bigscreen.mangindo.databinding.ActivityChapterListBinding;
import com.bigscreen.mangindo.info.MangaInfoActivity;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.model.Manga;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

public class ChapterListActivity extends BaseActivity implements OnLoadDataListener, OnListItemClickListener,
        View.OnClickListener {

    private ActivityChapterListBinding binding;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chapter_list);

        manga = getIntent().getParcelableExtra(IntentKey.MANGA_DATA);
        if (manga == null) {
            Log.e(Constant.LOG_TAG, "Could not found intent extra");
            finish();
        }

        chaptersAdapter = new ChaptersAdapter(this, this, manga.getHiddenComic(), storedDataService, apiService);
        setCollapsingToolbarContent();
        initRecyclerView();
        setToolbarTitle(manga.getTitle(), true);
        chaptersAdapter.loadChapters();
    }

    private void setCollapsingToolbarContent() {
        binding.toolbarCollapsing.setTitle(manga.getTitle());
        binding.imageMangaCover.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryOverlay_50));
        binding.buttonInfo.setOnClickListener(this);
        Glide.with(this).load(manga.getComicIcon())
                .error(R.drawable.ic_image_error)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(binding.imageMangaCover);
    }

    private void initRecyclerView() {
        binding.listChapters.setHasFixedSize(true);
        binding.listChapters.setLayoutManager(new LinearLayoutManager(this));
        binding.listChapters.setAdapter(chaptersAdapter);
        chaptersAdapter.setOnListItemClickListener(this);
    }

    @Override
    public void onPrepare() {
        binding.progressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        binding.progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorMessage) {
        binding.progressLoading.setVisibility(View.GONE);
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
