package com.markcadag.movieviewer.ui.moviedetails

import com.markcadag.movieviewer.util.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock


/**
 * Created by markcadag on 4/7/18.
 */
class MovieDetailsPresenterTest {

    @Mock
    var mMockMovieDetailsMvpView: MovieDetailsMvpView? = null
    @Mock
    var mMockMovieDetailsPresenter: MovieDetailsPresenter? = null

    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        mMockMovieDetailsPresenter = MovieDetailsPresenter()
        mMockMovieDetailsPresenter?.attachView(mMockMovieDetailsMvpView!!);

    }

    @After
    fun tearDown() {
        mMockMovieDetailsPresenter?.detachView()
    }

    @Test
    fun loadMovieReturnsMovie() {
//        val movie = TestDataFactory.makeMovie(10)

//        mMockMovieDetailsPresenter?.fetchMovie()
//        verify(mMockMovieDetailsMvpView)?.onLoadMovieDetails(ribots)
//        verify(mMockMovieDetailsMvpView, never())?.error()
    }

}