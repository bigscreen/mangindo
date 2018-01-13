package com.bigscreen.mangindo.content

import com.bigscreen.mangindo.common.Utils
import com.bigscreen.mangindo.network.NetworkError
import com.bigscreen.mangindo.network.model.response.MangaContentListResponse
import com.bigscreen.mangindo.network.service.MangaApiService
import rx.subscriptions.CompositeSubscription


class MangaContentLoader(private val apiService: MangaApiService, private val listener: OnLoadMangaContentListListener) {

    private val subscriptions = CompositeSubscription()

    fun unSubscribe() {
        subscriptions.unsubscribe()
    }

    fun loadContentList(mangaTitle: String, chapter: String) {
        listener.onPrepareLoadData()
        val subscription = apiService.getMangaContent(mangaTitle, chapter, object : MangaApiService.LoadMangaContentListCallback {
            override fun onSuccess(response: MangaContentListResponse?) {
                if (response != null)
                    listener.onSuccessLoadData(getNonAdsMangaContent(response))
                else
                    listener.onFailedLoadData("Cannot load chapter")
            }

            override fun onError(networkError: NetworkError) {
                listener.onFailedLoadData(networkError.errorMessage)
            }
        })
        subscriptions.add(subscription)
    }

    private fun getNonAdsMangaContent(mangaContent: MangaContentListResponse): MangaContentListResponse {
        val images = mangaContent.mangaImages
        val tempImages = images?.filter { Utils.isNotAdsUrl(it.url) }
        mangaContent.mangaImages = tempImages
        return mangaContent
    }

    interface OnLoadMangaContentListListener {

        fun onPrepareLoadData()

        fun onSuccessLoadData(mangaContentListResponse: MangaContentListResponse)

        fun onFailedLoadData(message: String)
    }
}