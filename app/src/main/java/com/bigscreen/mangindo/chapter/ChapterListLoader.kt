package com.bigscreen.mangindo.chapter

import com.bigscreen.mangindo.network.NetworkError
import com.bigscreen.mangindo.network.model.response.ChapterListResponse
import com.bigscreen.mangindo.network.service.MangaApiService
import rx.Subscription
import rx.subscriptions.CompositeSubscription


class ChapterListLoader(var apiService: MangaApiService, var listener: OnLoadChapterListListener) {

    private val subscriptions = CompositeSubscription()

    fun loadChapterList(mangaTitle: String) {
        listener.onPrepareLoadData()
        val subscription: Subscription = apiService.getChapters(mangaTitle, object: MangaApiService.LoadChapterListCallback {
            override fun onSuccess(response: ChapterListResponse?) {
                response?.let { listener.onSuccessLoadData(it) } ?: listener.onFailedLoadData("Chapter tidak dapat ditemukan")
            }

            override fun onError(networkError: NetworkError?) {
                listener.onFailedLoadData(networkError?.errorMessage ?: NetworkError.MESSAGE_ERROR)
                networkError?.let { listener.onFailedLoadData(it.errorMessage) }
            }
        })
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