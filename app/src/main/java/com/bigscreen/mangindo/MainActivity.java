package com.bigscreen.mangindo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigscreen.mangindo.adapter.NewReleaseAdapter;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.listener.OnListItemClickListener;
import com.bigscreen.mangindo.listener.OnLoadDataListener;
import com.bigscreen.mangindo.network.service.MangaApiService;
import com.bigscreen.mangindo.stored.StoredDataService;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnLoadDataListener, OnListItemClickListener {

    private SwipeRefreshLayout layoutSwipeRefresh;
    private RecyclerView gridMangaNewRelease;
    private ProgressBar progressLoading;
    private NewReleaseAdapter newReleaseAdapter;

    private FrameLayout layoutSearch;
    private EditText inputSearch;
    private Animation animSlideUp;
    private Animation animSlideDown;

    private boolean isLoading;

    @Inject
    StoredDataService storedDataService;

    @Inject
    MangaApiService mangaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppDeps().inject(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        layoutSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        gridMangaNewRelease = (RecyclerView) findViewById(R.id.grid_manga_new_release);
        progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
        layoutSearch = (FrameLayout) findViewById(R.id.layout_search);
        inputSearch = (EditText) findViewById(R.id.input_search);
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim);
        newReleaseAdapter = new NewReleaseAdapter(this, this, storedDataService, mangaApiService);
        layoutSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));

        gridMangaNewRelease.setLayoutManager(new GridLayoutManager(this, 3));
        gridMangaNewRelease.setAdapter(newReleaseAdapter);

        newReleaseAdapter.setOnListItemClickListener(this);
        newReleaseAdapter.loadManga();

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.ime_action_search || id == EditorInfo.IME_ACTION_SEARCH) {
                    newReleaseAdapter.filterList(inputSearch.getText().toString());
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newReleaseAdapter.filterList(editable.toString());
            }
        });
        layoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newReleaseAdapter.loadManga();
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textTitle = (TextView) findViewById(R.id.text_main_title);
        textTitle.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isLoading) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPrepare() {
        if (!layoutSwipeRefresh.isRefreshing())
            progressLoading.setVisibility(View.VISIBLE);
        isLoading = true;
        invalidateOptionsMenu();
    }

    @Override
    public void onSuccess() {
        layoutSwipeRefresh.setRefreshing(false);
        progressLoading.setVisibility(View.GONE);
        isLoading = false;
        invalidateOptionsMenu();
    }

    @Override
    public void onError(String errorMessage) {
        layoutSwipeRefresh.setRefreshing(false);
        progressLoading.setVisibility(View.GONE);
        showAlert("Error", errorMessage, "Reload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                newReleaseAdapter.loadManga();
            }
        });
    }

    @Override
    protected void onDestroy() {
        newReleaseAdapter.onParentDestroyed();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, ChapterListActivity.class);
        intent.putExtra(IntentKey.MANGA_KEY, newReleaseAdapter.getItem(position).getHiddenComic());
        intent.putExtra(IntentKey.MANGA_TITLE, newReleaseAdapter.getItem(position).getTitle());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: {
                if (layoutSearch.getVisibility() == View.VISIBLE) {
                    item.setIcon(R.drawable.ic_search_white);
                    item.setTitle(R.string.search);
                    hideSearchLayout();
                } else {
                    item.setIcon(R.drawable.ic_close_white);
                    item.setTitle(R.string.close_search);
                    showSearchLayout();
                }
                return true;
            }
            case R.id.action_sort_by_release: {
                item.setChecked(true);
                newReleaseAdapter.setSortingOption(NewReleaseAdapter.SORT_BY_DATE);
                showToast("Sorted by release date.");
                return true;
            }
            case R.id.action_sort_by_title: {
                item.setChecked(true);
                newReleaseAdapter.setSortingOption(NewReleaseAdapter.SORT_BY_TITLE);
                showToast("Sorted by manga title.");
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void hideSearchLayout() {
        animSlideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                inputSearch.clearFocus();
                layoutSearch.setVisibility(View.GONE);
                hideKeyboard();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layoutSearch.startAnimation(animSlideUp);
    }

    private void showSearchLayout() {
        layoutSearch.setVisibility(View.VISIBLE);
        animSlideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                inputSearch.requestFocus();
                showKeyboard(inputSearch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layoutSearch.startAnimation(animSlideDown);
    }

    @Override
    public void onBackPressed() {
        if (layoutSearch.getVisibility() == View.VISIBLE)
            hideSearchLayout();
        else
            super.onBackPressed();
    }
}
