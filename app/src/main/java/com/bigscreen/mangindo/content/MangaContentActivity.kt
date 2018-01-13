package com.bigscreen.mangindo.content

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.base.BaseActivity
import com.bigscreen.mangindo.common.Constant
import com.bigscreen.mangindo.common.IntentKey
import com.bigscreen.mangindo.databinding.ActivityMangaContentBinding
import com.bigscreen.mangindo.listener.OnContentImageClickListener
import com.bigscreen.mangindo.network.model.MangaImage
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse
import com.bigscreen.mangindo.network.service.MangaApiService
import com.bigscreen.mangindo.stored.StoredDataService
import javax.inject.Inject


class MangaContentActivity : BaseActivity(), MangaContentLoader.OnLoadMangaContentListListener, OnContentImageClickListener {

    @Inject lateinit var storedDataService: StoredDataService
    @Inject lateinit var apiService: MangaApiService

    private lateinit var binding: ActivityMangaContentBinding
    private lateinit var animSlideUp: Animation
    private lateinit var animSlideDown: Animation
    private lateinit var pagerAdapter: MangaContentPagerAdapter
    private lateinit var mangaContentLoader: MangaContentLoader

    private var pageSize = 0
    private var mangaKey = ""
    private var mangaTitle = ""
    private var chapterKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDeps.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manga_content)

        if (intent.hasExtra(IntentKey.MANGA_KEY) && intent.hasExtra(IntentKey.MANGA_TITLE)
                && intent.hasExtra(IntentKey.CHAPTER_KEY)) {
            mangaKey = intent.getStringExtra(IntentKey.MANGA_KEY)
            mangaTitle = intent.getStringExtra(IntentKey.MANGA_TITLE)
            chapterKey = intent.getStringExtra(IntentKey.CHAPTER_KEY)
        } else {
            Log.e(Constant.LOG_TAG, "Could not found intent extra")
            finish()
        }

        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim)
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim)
        pagerAdapter = MangaContentPagerAdapter(supportFragmentManager)
        mangaContentLoader = MangaContentLoader(apiService, this)

        setToolbarTitle(String.format("%s - %s", mangaTitle, chapterKey), true)
        setToolbarSubtitle(String.format(getString(R.string.page_), 1))

        binding.pagerMangaImages.adapter = pagerAdapter
        mangaContentLoader.loadContentList(mangaKey, chapterKey)

        binding.pagerMangaImages.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                setToolbarSubtitle(String.format(getString(R.string.manga_page_index), position + 1, pageSize))
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun onDestroy() {
        mangaContentLoader.unSubscribe()
        super.onDestroy()
    }

    override fun onPrepareLoadData() {
        binding.progressLoading.visibility = View.VISIBLE
    }

    override fun onSuccessLoadData(mangaContentListResponse: MangaContentListResponse) {
        binding.progressLoading.visibility = View.GONE
        setContent(mangaContentListResponse.mangaImages ?: emptyList())
        storedDataService.saveContentOfComic(mangaKey, chapterKey, mangaContentListResponse)
    }

    override fun onFailedLoadData(message: String) {
        binding.progressLoading.visibility = View.GONE
        storedDataService.pullStoredContentOfComic(mangaKey, chapterKey,
                object : StoredDataService.OnGetSavedDataListener<MangaContentListResponse> {
                    override fun onDataFound(savedData: MangaContentListResponse) {
                        binding.progressLoading.visibility = View.GONE
                        setContent(savedData.mangaImages ?: emptyList())
                    }

                    override fun onDataNotFound() {
                        showAlert("Error", message, "Reload") { _, _ ->
                            mangaContentLoader.loadContentList(mangaKey, chapterKey)
                        }
                    }
                })
    }

    override fun OnContentImageClick(position: Int) {
        if (toolbar.visibility == View.VISIBLE)
            hideToolbar()
        else
            showToolbar()
    }

    private fun setContent(mangaImages: List<MangaImage>) {
        pageSize = mangaImages.size
        pagerAdapter.fragments = mangaImages.map { MangaContentFragment.getInstance(it.url) }
        setToolbarSubtitle(String.format(getString(R.string.manga_page_index), 1, pageSize))
    }

    private fun hideToolbar() {
        if (toolbar.visibility == View.GONE) return
        animSlideUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                toolbar.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        toolbar.startAnimation(animSlideUp)
    }

    private fun showToolbar() {
        if (toolbar.visibility == View.VISIBLE) return
        toolbar.visibility = View.VISIBLE
        toolbar.startAnimation(animSlideDown)
    }
}