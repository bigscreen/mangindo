package com.bigscreen.mangindo.newrelease

import com.bigscreen.mangindo.network.NetworkError
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse
import com.bigscreen.mangindo.network.service.MangaApiService

import rx.subscriptions.CompositeSubscription


class NewReleaseLoader(private val apiService: MangaApiService, private val listener: OnLoadNewReleaseListener) {

    private val subscriptions = CompositeSubscription()

    fun loadNewReleaseManga() {
        listener.onPrepareLoadData()
        val subscription = apiService.getNewReleaseManga(object : MangaApiService.LoadMangaListCallback {

            override fun onSuccess(response: NewReleaseResponse?) {
                if (response != null)
                    listener.onSuccessLoadData(response)
                else
                    listener.onFailedLoadData("Manga tidak dapat ditemukan")
            }

            override fun onError(networkError: NetworkError) {
                listener.onFailedLoadData(networkError.getErrorMessage())
            }
        })
        subscriptions.add(subscription)
    }

    fun unSubscribe() {
        subscriptions.unsubscribe()
    }

    interface OnLoadNewReleaseListener {

        fun onPrepareLoadData()

        fun onSuccessLoadData(newReleaseResponse: NewReleaseResponse)

        fun onFailedLoadData(message: String)
    }
}
