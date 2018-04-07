package com.markcadag.movieviewer.ui.moviedetails

import com.markcadag.movieviewer.model.Movie
import com.markcadag.movieviewer.ui.base.MvpView

/**
 * Created by markcadag on 4/7/18.
 */
interface MovieDetailsMvpView : MvpView {
    fun onLoadMovieDetails(movie: Movie)

}