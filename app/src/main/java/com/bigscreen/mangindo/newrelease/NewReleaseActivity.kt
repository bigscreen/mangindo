package com.bigscreen.mangindo.newrelease

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.base.BaseActivity
import com.bigscreen.mangindo.chapter.ChapterListActivity
import com.bigscreen.mangindo.common.IntentKey
import com.bigscreen.mangindo.common.extension.hideKeyboard
import com.bigscreen.mangindo.common.extension.showAlert
import com.bigscreen.mangindo.common.extension.showKeyboard
import com.bigscreen.mangindo.common.extension.showToast
import com.bigscreen.mangindo.common.listener.OnListItemClickListener
import com.bigscreen.mangindo.common.listener.OnLoadDataListener
import com.bigscreen.mangindo.repos.MangaRepository
import com.bigscreen.mangindo.stored.StoredDataService
import kotlinx.android.synthetic.main.activity_new_release.gridMangaNewRelease
import kotlinx.android.synthetic.main.activity_new_release.inputSearch
import kotlinx.android.synthetic.main.activity_new_release.layoutSearch
import kotlinx.android.synthetic.main.activity_new_release.layoutSwipeRefresh
import kotlinx.android.synthetic.main.activity_new_release.progressLoading
import javax.inject.Inject

class NewReleaseActivity : BaseActivity(), OnLoadDataListener, OnListItemClickListener {

    companion object {
        private const val IME_ACTION_SEARCH = 10
    }

    @Inject lateinit var storedDataService: StoredDataService
    @Inject lateinit var mangaRepository: MangaRepository

    private lateinit var newReleaseAdapter: NewReleaseAdapter
    private lateinit var animSlideUp: Animation
    private lateinit var animSlideDown: Animation

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDeps.inject(this)
        setContentView(R.layout.activity_new_release)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim)
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim)
        newReleaseAdapter = NewReleaseAdapter(this, this, storedDataService, mangaRepository)

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
        val textTitle = findViewById<TextView>(R.id.text_main_title)
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
        showAlert(
                "Error",
                errorMessage,
                "Reload",
                DialogInterface.OnClickListener {  _, _->
                    newReleaseAdapter.loadManga()
                }
        )
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
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
                this@NewReleaseActivity.hideKeyboard()
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
                this@NewReleaseActivity.showKeyboard(inputSearch)
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