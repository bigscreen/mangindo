package com.bigscreen.mangindo.newrelease

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.stored.StoredDataService
import com.bigscreen.mangindo.listener.OnListItemClickListener
import com.bigscreen.mangindo.listener.OnLoadDataListener
import com.bigscreen.mangindo.network.model.Manga
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse
import com.bigscreen.mangindo.network.service.MangaApiService
import java.util.Collections

class NewReleaseAdapter(
        private val context: Context,
        private val loadDataListener: OnLoadDataListener,
        private val storedDataService: StoredDataService,
        apiService: MangaApiService
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        NewReleaseLoader.OnLoadNewReleaseListener,
        NewReleaseViewHolder.OnMangaClickListener {

    companion object {
        const val SORT_BY_DATE = 1
        const val SORT_BY_TITLE = 2
    }

    private val newReleaseLoader = NewReleaseLoader(apiService, this)
    private var backupMangaList = emptyList<Manga>()
    private var mangaList = emptyList<Manga>()
        set(value) {
            field = value
            backupMangaList = value
            notifyDataSetChanged()
        }

    var listItemClickListener: OnListItemClickListener? = null

    private var sortBy = SORT_BY_DATE
    private var searchKeyword = ""

    fun loadManga() {
        newReleaseLoader.loadNewReleaseManga()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_new_release, parent, false)
        return NewReleaseViewHolder(view, context, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (searchKeyword.isEmpty())
            (holder as NewReleaseViewHolder).bindData(getItem(position))
        else
            (holder as NewReleaseViewHolder).bindDataSearch(getItem(position), searchKeyword)
    }

    override fun getItemCount(): Int = mangaList.size

    override fun onPrepareLoadData() {
        loadDataListener.onPrepare()
    }

    override fun onSuccessLoadData(newReleaseResponse: NewReleaseResponse) {
        loadDataListener.onSuccess()
        mangaList = newReleaseResponse.comics
        storedDataService.saveNewReleasedComic(newReleaseResponse)
    }

    override fun onFailedLoadData(message: String) {
        loadDataFromPreference(message)
    }

    override fun onMangaClick(position: Int) {
        listItemClickListener?.onListItemClick(position)
    }

    fun getItem(position: Int): Manga = mangaList[position]

    fun onParentDestroyed() {
        newReleaseLoader.unSubscribe()
    }

    fun filterList(keyword: String) {
        searchKeyword = keyword
        mangaList = getBackupMangaList()
        if (keyword.isNotEmpty()) {
            mangaList = mangaList.filter { isStartWith(it.title, keyword) || isStartWith(it.hiddenComic, keyword) }
        }
        notifyDataSetChanged()
    }

    private fun loadDataFromPreference(networkErrorMessage: String) {
        storedDataService.pullStoredNewReleasedComic(object : StoredDataService.OnGetSavedDataListener<NewReleaseResponse> {
            override fun onDataFound(savedData: NewReleaseResponse) {
                loadDataListener.onSuccess()
                mangaList = savedData.comics
            }

            override fun onDataNotFound() {
                loadDataListener.onError(networkErrorMessage)
            }
        })
    }

    fun setSortingOption(sortBy: Int) {
        if (this.sortBy == sortBy) return
        this.sortBy = sortBy
        when (sortBy) {
            SORT_BY_DATE -> sortMangaListByDate()
            SORT_BY_TITLE -> sortMangaListByName()
        }
    }

    private fun sortMangaListByDate() {
        Collections.sort(mangaList) { manga, mangaComparator ->
            val id = manga?.id ?: 0
            val idComparator = mangaComparator?.id ?: 0
            id - idComparator
        }
        notifyDataSetChanged()
    }

    private fun sortMangaListByName() {
        Collections.sort(mangaList) { manga, mangaComparator ->
            val title = manga?.title?.toLowerCase() ?: ""
            val titleComparator = mangaComparator?.title?.toLowerCase() ?: ""
            title.compareTo(titleComparator)
        }
        notifyDataSetChanged()
    }

    private fun isStartWith(firstArg: String, secondArg: String): Boolean {
        return firstArg.toLowerCase().startsWith(secondArg.toLowerCase())
    }

    private fun getBackupMangaList(): List<Manga> {
        return when (sortBy) {
            SORT_BY_DATE -> {
                backupMangaList.sortedWith(Comparator { manga, mangaComparator ->
                    val id = manga?.id ?: 0
                    val idComparator = mangaComparator?.id ?: 0
                    id - idComparator
                })
            }
            SORT_BY_TITLE -> {
                backupMangaList.sortedWith(Comparator { manga, mangaComparator ->
                    val title = manga?.title?.toLowerCase() ?: ""
                    val titleComparator = mangaComparator?.title?.toLowerCase() ?: ""
                    title.compareTo(titleComparator)
                })
            }
            else -> emptyList()
        }
    }

}

