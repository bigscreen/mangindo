package com.bigscreen.mangindo.manga.content;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.listener.OnContentImageClickListener;
import com.bigscreen.mangindo.network.model.MangaImage;
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MangaContentActivity extends BaseActivity implements MangaContentLoader.OnLoadMangaContentListListener, OnContentImageClickListener {

    private ViewPager pagerMangaImages;
    private ProgressBar progressLoading;
    private Animation animSlideUp;
    private Animation animSlideDown;
    private MangaContentPagerAdapter pagerAdapter;

    private MangaContentLoader mangaContentLoader;

    private int pageSize;
    private String mangaKey = "", mangaTitle = "", chapterKey = "";

    @Inject
    StoredDataService storedDataService;

    @Inject
    MangaApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppDeps().inject(this);
        setContentView(R.layout.activity_manga_content);

        if (getIntent().hasExtra(IntentKey.MANGA_KEY) && getIntent().hasExtra(IntentKey.MANGA_TITLE)
                && getIntent().hasExtra(IntentKey.CHAPTER_KEY)) {
            mangaKey = getIntent().getStringExtra(IntentKey.MANGA_KEY);
            mangaTitle = getIntent().getStringExtra(IntentKey.MANGA_TITLE);
            chapterKey = getIntent().getStringExtra(IntentKey.CHAPTER_KEY);
        } else {
            Log.e(Constant.LOG_TAG, "Could not found intent extra");
            finish();
        }

        pagerMangaImages = (ViewPager) findViewById(R.id.pager_manga_images);
        progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim);
        pagerAdapter = new MangaContentPagerAdapter(getSupportFragmentManager());
        mangaContentLoader = new MangaContentLoader(apiService, this);

        setToolbarTitle(mangaTitle, true);
        setToolbarSubtitle(String.format(getString(R.string.page_), 1));

        pagerMangaImages.setAdapter(pagerAdapter);
        mangaContentLoader.loadContentList(mangaKey, chapterKey);

        pagerMangaImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSubtitle(String.format(getString(R.string.manga_page_index), (position + 1), pageSize));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        mangaContentLoader.unSubscribe();
        super.onDestroy();
    }

    @Override
    public void onPrepareLoadData() {
        progressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessLoadData(MangaContentListResponse mangaContentListResponse) {
        progressLoading.setVisibility(View.GONE);
        setContent(mangaContentListResponse.getChapter());
        storedDataService.saveContentOfComic(mangaKey, chapterKey, mangaContentListResponse);
    }

    @Override
    public void onFailedLoadData(final String message) {
        progressLoading.setVisibility(View.GONE);
        storedDataService.pullStoredContentOfComic(mangaKey, chapterKey,
                new StoredDataService.OnGetSavedDataListener<MangaContentListResponse>() {
            @Override
            public void onDataFound(MangaContentListResponse savedData) {
                progressLoading.setVisibility(View.GONE);
                setContent(savedData.getChapter());
            }

            @Override
            public void onDataNotFound() {
                showAlert("Error", message, "Reload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mangaContentLoader.loadContentList(mangaKey, chapterKey);
                    }
                });
            }
        });
    }

    private void setContent(List<MangaImage> mangaImages) {
        pageSize = mangaImages.size();
        List<MangaContentFragment> fragments = new ArrayList<>();
        for (MangaImage mangaImage : mangaImages) {
            fragments.add(MangaContentFragment.getInstance(mangaImage.getUrl()));
        }
        pagerAdapter.setFragments(fragments);
        setToolbarSubtitle(String.format(getString(R.string.manga_page_index), 1, pageSize));
    }

    @Override
    public void OnContentImageClick(int position) {
        if (toolbar.getVisibility() == View.VISIBLE)
            hideToolbar();
        else
            showToolbar();
    }

    private void hideToolbar() {
        if (toolbar.getVisibility() == View.GONE) return;
        animSlideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        toolbar.startAnimation(animSlideUp);
    }

    private void showToolbar() {
        if (toolbar.getVisibility() == View.VISIBLE) return;
        toolbar.setVisibility(View.VISIBLE);
        toolbar.startAnimation(animSlideDown);
    }

}
