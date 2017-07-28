package com.bigscreen.mangindo.chapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.common.Constant
import com.bigscreen.mangindo.databinding.ItemChapterBinding
import com.bigscreen.mangindo.listener.OnListItemClickListener
import com.bigscreen.mangindo.listener.OnLoadDataListener
import com.bigscreen.mangindo.network.model.Chapter
import com.bigscreen.mangindo.network.model.response.ChapterListResponse
import com.bigscreen.mangindo.network.service.MangaApiService
import com.bigscreen.mangindo.stored.StoredDataService

class ChaptersAdapter(var context: Context, var loadDataListener: OnLoadDataListener,
                      var comicHiddenKey: String, var storedDataService: StoredDataService, var apiService: MangaApiService)
    : RecyclerView.Adapter<ChapterViewHolder>(), ChapterListLoader.OnLoadChapterListListener, ChapterViewHolder.OnChapterClickListener {

    private var chapterListLoader: ChapterListLoader = ChapterListLoader(apiService, this)

    var chapters: List<Chapter> = arrayListOf()
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listItemClickListener: OnListItemClickListener? = null
        get() = field
        set(value) {
            field = value
        }

    fun loadChapters() {
        chapterListLoader.loadChapterList(comicHiddenKey)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChapterViewHolder {
        val binding: ItemChapterBinding = DataBindingUtil.inflate<ItemChapterBinding>(LayoutInflater.from(context),
                R.layout.item_chapter, parent, false)
        return ChapterViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder?, position: Int) {
        holder?.setViewModel(ChapterItemViewModel(context.resources, getItem(position)))
    }

    override fun getItemCount(): Int = chapters.size

    fun getItem(position: Int): Chapter? = if (itemCount > 0 && position < itemCount) chapters[position] else null

    override fun onPrepareLoadData() {
        Log.d(Constant.LOG_TAG, "load data...")
        loadDataListener.onPrepare()
    }

    override fun onSuccessLoadData(chapterListResponse: ChapterListResponse?) {
        Log.d(Constant.LOG_TAG, "success load data \n" + chapterListResponse.toString())
        loadDataListener.onSuccess()
        chapterListResponse?.let { chapters = it.comics }
        storedDataService.saveChapterOfComic(comicHiddenKey, chapterListResponse)
    }

    override fun onFailedLoadData(message: String?) {
        Log.e(Constant.LOG_TAG, "failed load data, " + message)
        message?.let { loadDataFromStoredService(it) }
    }

    override fun onChapterClick(position: Int) {
        listItemClickListener?.onListItemClick(position)
    }

    fun onParentDestroyed() {
        chapterListLoader?.unSubscribe()
    }

    private fun loadDataFromStoredService(networkErrorMessage: String) {
        storedDataService.pullStoredChapterOfComic(comicHiddenKey, object : StoredDataService.OnGetSavedDataListener<ChapterListResponse> {
            override fun onDataFound(savedData: ChapterListResponse?) {
                loadDataListener.onSuccess()
                savedData?.let { chapters = it.comics }
            }

            override fun onDataNotFound() {
                loadDataListener.onError(networkErrorMessage)
            }
        })
    }

}
