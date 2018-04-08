package com.markcadag.movieviewer.ui.seatmap

import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.Schedule
import com.markcadag.movieviewer.model.SeatMap
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
open class SeatMapPresenter : BasePresenter<SeatMapMvpView>() {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    fun fetchSeatMap() {

        val apiService = ServiceGenerator.createService(ApiService::class.java)
                .getSeatMap()

        compositeDisposable.add(apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<SeatMap>>() {
                    override fun onError(e: Throwable) {
                        mvpView?.onError(R.string.failed_to_fetch_movie)
                    }

                    override fun onNext(t: Response<SeatMap>) {
                        if(!t.isSuccessful) {
                            mvpView?.onError(R.string.failed_to_fetch_movie)
                            return
                        }
                        t.body()?.let {
                            mvpView?.onLoadSeatMap(it)
                        }
                    }

                    override fun onComplete() { }
                }))
    }

    fun fetchSchedule() {

        val apiService = ServiceGenerator.createService(ApiService::class.java)
                .getSchedule()

        compositeDisposable.add(apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<Schedule>>() {
                    override fun onError(e: Throwable) {
                        mvpView?.onError(R.string.failed_to_fetch_movie)
                    }

                    override fun onNext(t: Response<Schedule>) {
                        if(!t.isSuccessful) {
                            mvpView?.onError(R.string.failed_to_fetch_movie)
                            return
                        }
                        t.body()?.let {
                            mvpView?.onLoadSchedule(it)
                        }
                    }

                    override fun onComplete() { }
                }))


    }
}