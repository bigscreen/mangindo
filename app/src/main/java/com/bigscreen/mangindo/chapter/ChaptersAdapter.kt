package com.bigscreen.mangindo.chapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.listener.OnListItemClickListener
import com.bigscreen.mangindo.listener.OnLoadDataListener
import com.bigscreen.mangindo.network.model.Chapter
import com.bigscreen.mangindo.network.model.response.ChapterListResponse
import com.bigscreen.mangindo.network.service.MangaApiService
import com.bigscreen.mangindo.stored.StoredDataService

class ChaptersAdapter(private val context: Context,
                      private val loadDataListener: OnLoadDataListener,
                      private val comicHiddenKey: String,
                      private val storedDataService: StoredDataService,
                      apiService: MangaApiService)
    : RecyclerView.Adapter<ChapterViewHolder>(), ChapterListLoader.OnLoadChapterListListener, ChapterViewHolder.OnChapterClickListener {

    private var chapterListLoader: ChapterListLoader = ChapterListLoader(apiService, this)

    var chapters: List<Chapter> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listItemClickListener: OnListItemClickListener? = null

    fun loadChapters() {
        chapterListLoader.loadChapterList(comicHiddenKey)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.setViewModel(ChapterItemViewModel(context.resources, getItem(position)))
    }

    override fun getItemCount(): Int = chapters.size

    fun getItem(position: Int): Chapter? = if (itemCount > 0 && position < itemCount) chapters[position] else null

    override fun onPrepareLoadData() {
        loadDataListener.onPrepare()
    }

    override fun onSuccessLoadData(chapterListResponse: ChapterListResponse?) {
        loadDataListener.onSuccess()
        chapterListResponse?.let { chapters = it.comics }
        storedDataService.saveChapterOfComic(comicHiddenKey, chapterListResponse)
    }

    override fun onFailedLoadData(message: String) {
        loadDataFromStoredService(message)
    }

    override fun onChapterClick(position: Int) {
        listItemClickListener?.onListItemClick(position)
    }

    fun onParentDestroyed() {
        chapterListLoader.unSubscribe()
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
