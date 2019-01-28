package com.bigscreen.mangindo.arch

import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<in M, V : BaseView<M>> {
    protected var s = CompositeSubscription()
    private lateinit var view: V

    fun attach(v: V) {
        this.view = v
        onAttach()
    }

    fun detach() {
        s.clear()
        onDetach()
    }

    fun view(): V = view

    protected fun observe(execute: () -> Subscription) {
        s.add(execute())
    }

    protected open fun onAttach() {}
    protected open fun onDetach() {}
}