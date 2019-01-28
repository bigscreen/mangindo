package com.bigscreen.mangindo.common.extension

import com.bigscreen.mangindo.network.NetworkError
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.configured(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext { throwable: Throwable -> Observable.error(throwable)}
}

fun <T> Observable<T>.subscribes(success: (T) -> Unit, error: (NetworkError) -> Unit): Subscription {
    return subscribe({ success(it) }, { error(NetworkError(it)) })
}
