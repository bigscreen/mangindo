package com.bigscreen.mangindo.chapter

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.base.BaseActivity
import com.bigscreen.mangindo.common.IntentKey
import com.bigscreen.mangindo.common.extension.setToolbarTitle
import com.bigscreen.mangindo.common.extension.showAlert
import com.bigscreen.mangindo.content.MangaContentActivity
import com.bigscreen.mangindo.info.MangaInfoActivity
import com.bigscreen.mangindo.common.listener.OnListItemClickListener
import com.bigscreen.mangindo.common.listener.OnLoadDataListener
import com.bigscreen.mangindo.network.model.Manga
import com.bigscreen.mangindo.repos.MangaRepository
import com.bigscreen.mangindo.stored.StoredDataService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_chapter_list.buttonInfo
import kotlinx.android.synthetic.main.activity_chapter_list.imageMangaCover
import kotlinx.android.synthetic.main.activity_chapter_list.listChapters
import kotlinx.android.synthetic.main.activity_chapter_list.progressLoading
import kotlinx.android.synthetic.main.activity_chapter_list.toolbarCollapsing
import javax.inject.Inject

class ChapterListActivity : BaseActivity(), OnLoadDataListener, OnListItemClickListener {

    @Inject lateinit var storedDataService: StoredDataService
    @Inject lateinit var mangaRepository: MangaRepository

    private lateinit var chaptersAdapter: ChaptersAdapter
    private lateinit var manga: Manga

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDeps.inject(this)
        setContentView(R.layout.activity_chapter_list)

        if (intent.hasExtra(IntentKey.MANGA_DATA)) {
            manga = intent.getParcelableExtra(IntentKey.MANGA_DATA)
        } else {
            finish()
        }

        chaptersAdapter = ChaptersAdapter(this, this, manga.hiddenComic, storedDataService, mangaRepository)
        setCollapsingToolbarContent()
        initRecyclerView()
        setToolbarTitle(manga.title, true)
        chaptersAdapter.loadChapters()
    }

    override fun onDestroy() {
        chaptersAdapter.onParentDestroyed()
        super.onDestroy()
    }

    private fun setCollapsingToolbarContent() {
        toolbarCollapsing.title = manga.title
        imageMangaCover.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryOverlay_50))
        Glide.with(this).load(manga.comicIcon)
                .error(R.drawable.ic_image_error)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageMangaCover)
        buttonInfo.setOnClickListener {
            val intent = Intent(this@ChapterListActivity, MangaInfoActivity::class.java)
            intent.putExtra(IntentKey.MANGA_DATA, manga)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        listChapters.setHasFixedSize(true)
        listChapters.layoutManager = LinearLayoutManager(this)
        listChapters.adapter = chaptersAdapter
        chaptersAdapter.listItemClickListener = this
    }

    override fun onListItemClick(position: Int) {
        chaptersAdapter.getItem(position)?.let {
            val intent = Intent(this@ChapterListActivity, MangaContentActivity::class.java)
            intent.putExtra(IntentKey.MANGA_KEY, manga.hiddenComic)
            intent.putExtra(IntentKey.MANGA_TITLE, manga.title)
            intent.putExtra(IntentKey.CHAPTER_KEY, it.hiddenChapter)
            startActivity(intent)
        }
    }

    override fun onPrepare() {
        progressLoading.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressLoading.visibility = View.GONE
    }

    override fun onError(errorMessage: String) {
        progressLoading.visibility = View.GONE
        showAlert(
                "Oops!",
                errorMessage,
                "Reload",
                DialogInterface.OnClickListener { _, _ ->
                    chaptersAdapter.loadChapters()
                }
        )
    }
}