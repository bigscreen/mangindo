package com.bigscreen.mangindo.arch

import rx.Observable

interface BaseView<in M> {
    fun showData(model: M): Observable<out UserAction>
    fun showError(error: Throwable): Observable<out UserAction> = Observable.never()
    fun showLoading() {}
    fun back() {}
    fun actions(): Observable<UserAction> = Observable.never()
}
