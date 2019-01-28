package com.bigscreen.mangindo.newrelease

import com.bigscreen.mangindo.common.extension.subscribes
import com.bigscreen.mangindo.network.model.response.NewReleaseResponse
import com.bigscreen.mangindo.repos.MangaRepository

import rx.subscriptions.CompositeSubscription

class NewReleaseLoader(
        private val repository: MangaRepository,
        private val listener: OnLoadNewReleaseListener
) {

    private val subscriptions = CompositeSubscription()

    fun loadNewReleaseManga() {
        listener.onPrepareLoadData()
        val subscription = repository.getNewReleased().subscribes(
                { listener.onSuccessLoadData(it) },
                { listener.onFailedLoadData(it.getErrorMessage()) }
        )
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
