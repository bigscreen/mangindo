package com.bigscreen.mangindo.newrelease

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.base.BaseActivity
import com.bigscreen.mangindo.chapter.ChapterListActivity
import com.bigscreen.mangindo.common.IntentKey
import com.bigscreen.mangindo.listener.OnListItemClickListener
import com.bigscreen.mangindo.listener.OnLoadDataListener
import com.bigscreen.mangindo.network.service.MangaApiService
import com.bigscreen.mangindo.stored.StoredDataService
import javax.inject.Inject


class NewReleaseActivity : BaseActivity(), OnLoadDataListener, OnListItemClickListener {

    companion object {
        private const val IME_ACTION_SEARCH = 10
    }

    @Inject lateinit var storedDataService: StoredDataService
    @Inject lateinit var mangaApiService: MangaApiService

    private lateinit var layoutSwipeRefresh: SwipeRefreshLayout
    private lateinit var gridMangaNewRelease: RecyclerView
    private lateinit var progressLoading: ProgressBar
    private lateinit var newReleaseAdapter: NewReleaseAdapter

    private lateinit var layoutSearch: FrameLayout
    private lateinit var inputSearch: EditText
    private lateinit var animSlideUp: Animation
    private lateinit var animSlideDown: Animation

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDeps.inject(this)
        setContentView(R.layout.activity_new_release)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        layoutSwipeRefresh = findViewById(R.id.layout_swipe_refresh) as SwipeRefreshLayout
        gridMangaNewRelease = findViewById(R.id.grid_manga_new_release) as RecyclerView
        progressLoading = findViewById(R.id.progress_loading) as ProgressBar
        layoutSearch = findViewById(R.id.layout_search) as FrameLayout
        inputSearch = findViewById(R.id.input_search) as EditText
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim)
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim)
        newReleaseAdapter = NewReleaseAdapter(this, this, storedDataService, mangaApiService)

        layoutSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        gridMangaNewRelease.adapter = newReleaseAdapter
        newReleaseAdapter.listItemClickListener = this
        inputSearch.setOnEditorActionListener(editorActionListener)
        inputSearch.addTextChangedListener(searchTextWatcher)
        layoutSwipeRefresh.setOnRefreshListener(pullRefreshListener)

        newReleaseAdapter.loadManga()
    }

    private val editorActionListener = TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEARCH) {
            newReleaseAdapter.filterList(inputSearch.text.toString())
            hideKeyboard()
            return@OnEditorActionListener true
        }
        false
    }

    private val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable) {
            newReleaseAdapter.filterList(editable.toString())
        }
    }

    private val pullRefreshListener = SwipeRefreshLayout.OnRefreshListener { newReleaseAdapter.loadManga() }

    override fun setTitle(title: CharSequence) {
        super.setTitle(title)
        val textTitle = findViewById(R.id.text_main_title) as TextView
        textTitle.text = title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!isLoading) {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_main, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepare() {
        if (!layoutSwipeRefresh.isRefreshing)
            progressLoading.visibility = View.VISIBLE
        isLoading = true
        invalidateOptionsMenu()
    }

    override fun onSuccess() {
        layoutSwipeRefresh.isRefreshing = false
        progressLoading.visibility = View.GONE
        isLoading = false
        invalidateOptionsMenu()
    }

    override fun onError(errorMessage: String) {
        layoutSwipeRefresh.isRefreshing = false
        progressLoading.visibility = View.GONE
        showAlert("Error", errorMessage, "Reload") { _, _->
            newReleaseAdapter.loadManga()
        }
    }

    override fun onDestroy() {
        newReleaseAdapter.onParentDestroyed()
        super.onDestroy()
    }

    override fun onListItemClick(position: Int) {
        val intent = Intent(this, ChapterListActivity::class.java)
        intent.putExtra(IntentKey.MANGA_DATA, newReleaseAdapter.getItem(position))
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                if (layoutSearch.visibility == View.VISIBLE) {
                    item.setIcon(R.drawable.ic_search_white)
                    item.setTitle(R.string.search)
                    hideSearchLayout()
                } else {
                    item.setIcon(R.drawable.ic_close_white)
                    item.setTitle(R.string.close_search)
                    showSearchLayout()
                }
                return true
            }
            R.id.action_sort_by_release -> {
                item.isChecked = true
                newReleaseAdapter.setSortingOption(NewReleaseAdapter.SORT_BY_DATE)
                showToast("Sorted by release date.")
                return true
            }
            R.id.action_sort_by_title -> {
                item.isChecked = true
                newReleaseAdapter.setSortingOption(NewReleaseAdapter.SORT_BY_TITLE)
                showToast("Sorted by manga title.")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun clearSearchClick(@Suppress("UNUSED_PARAMETER") view: View) {
        inputSearch.setText("")
    }

    private fun hideSearchLayout() {
        animSlideUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                inputSearch.clearFocus()
                layoutSearch.visibility = View.GONE
                hideKeyboard()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        layoutSearch.startAnimation(animSlideUp)
    }

    private fun showSearchLayout() {
        layoutSearch.visibility = View.VISIBLE
        animSlideDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                inputSearch.requestFocus()
                showKeyboard(inputSearch)
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        layoutSearch.startAnimation(animSlideDown)
    }

    override fun onBackPressed() {
        if (layoutSearch.visibility == View.VISIBLE)
            hideSearchLayout()
        else
            super.onBackPressed()
    }
}