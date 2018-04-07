package com.markcadag.movieviewer.ui.moviedetails

import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.Movie
import com.markcadag.movieviewer.service.ApiService
import com.markcadag.movieviewer.service.ServiceGenerator
import com.markcadag.movieviewer.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by markcadag on 4/7/18.
 */
open class MovieDetailsPresenter() : BasePresenter<MovieDetailsMvpView>() {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    fun fetchMovie() {

        val apiService = ServiceGenerator.createService(ApiService::class.java)
                .getMovieDetails()

        compositeDisposable.add(apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<Movie>>() {
                    override fun onError(e: Throwable) {
                        mvpView?.onError(R.string.failed_to_fetch_movie)
                    }

                    override fun onNext(t: Response<Movie>) {
                        if(!t.isSuccessful) {
                            mvpView?.onError(R.string.failed_to_fetch_movie)
                            return
                        }
                        t.body()?.let {
                            mvpView?.onLoadMovieDetails(it)
                        }
                    }

                    override fun onComplete() { }
                }))
    }
}