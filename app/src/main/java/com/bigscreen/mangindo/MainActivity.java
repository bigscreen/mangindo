package com.bigscreen.mangindo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnLoadDataListener, OnListItemClickListener {

    private RecyclerView gridMangaNewRelease;
    private ProgressBar progressLoading;
    private NewReleaseAdapter newReleaseAdapter;

    private FrameLayout layoutSearch;
    private EditText inputSearch;
    private Animation animSlideUp;
    private Animation animSlideDown;

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
        layoutSearch = (FrameLayout) findViewById(R.id.layout_search);
        inputSearch = (EditText) findViewById(R.id.input_search);
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim);
        newReleaseAdapter = new NewReleaseAdapter(this, this, mangaApiService);

        gridMangaNewRelease.setLayoutManager(new GridLayoutManager(this, 3));
        gridMangaNewRelease.setAdapter(newReleaseAdapter);

        newReleaseAdapter.setOnListItemClickListener(this);
        newReleaseAdapter.loadManga();

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.ime_action_search || id == EditorInfo.IME_NULL) {
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
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textTitle = (TextView) findViewById(R.id.text_main_title);
        textTitle.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
        intent.putExtra(IntentKey.MANGA_KEY, newReleaseAdapter.getItem(position).getHiddenKomik());
        intent.putExtra(IntentKey.MANGA_TITLE, newReleaseAdapter.getItem(position).getJudul());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: {
                if (layoutSearch.getVisibility() == View.VISIBLE)
                    hideSearchLayout();
                else
                    showSearchLayout();
                return true;
            }
            case R.id.action_sort_by_release: {
                newReleaseAdapter.setSortingOption(NewReleaseAdapter.SORT_BY_DATE);
                showToast("Sorted by release date.");
                return true;
            }
            case R.id.action_sort_by_title: {
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
