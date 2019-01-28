package com.bigscreen.mangindo.content

import com.bigscreen.mangindo.common.extension.subscribes
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse
import com.bigscreen.mangindo.repos.MangaRepository
import rx.subscriptions.CompositeSubscription


class MangaContentLoader(
        private val repository: MangaRepository,
        private val listener: OnLoadMangaContentListListener
) {

    private val subscriptions = CompositeSubscription()

    fun unSubscribe() {
        subscriptions.unsubscribe()
    }

    fun loadContentList(mangaTitle: String, chapter: String) {
        listener.onPrepareLoadData()
        val subscription = repository.getContents(mangaTitle, chapter).subscribes(
                { listener.onSuccessLoadData(it.getNonAdsMangaContent()) },
                { listener.onFailedLoadData(it.getErrorMessage()) }
        )
        subscriptions.add(subscription)
    }

    interface OnLoadMangaContentListListener {

        fun onPrepareLoadData()

        fun onSuccessLoadData(mangaContentListResponse: MangaContentListResponse)

        fun onFailedLoadData(message: String)
    }
}
