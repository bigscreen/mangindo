package com.bigscreen.mangindo.chapter

import com.bigscreen.mangindo.common.extension.subscribes
import com.bigscreen.mangindo.network.model.response.ChapterListResponse
import com.bigscreen.mangindo.repos.MangaRepository
import rx.subscriptions.CompositeSubscription

class ChapterListLoader(
        var repository: MangaRepository,
        var listener: OnLoadChapterListListener
) {

    private val subscriptions = CompositeSubscription()

    fun loadChapterList(mangaTitle: String) {
        listener.onPrepareLoadData()
        val subscription = repository.getChapters(mangaTitle).subscribes(
                { listener.onSuccessLoadData(it) },
                { listener.onFailedLoadData(it.getErrorMessage()) }
        )
        subscriptions.add(subscription)
    }

    fun unSubscribe() {
        subscriptions.unsubscribe()
    }

    interface OnLoadChapterListListener {
        fun onPrepareLoadData()

        fun onSuccessLoadData(chapterListResponse: ChapterListResponse?)

        fun onFailedLoadData(message: String)
    }
}