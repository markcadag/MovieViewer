package com.markcadag.movieviewer.ui.base

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface Presenter<V> {

    fun attachView(mvpView: V)

    fun detachView()
}
