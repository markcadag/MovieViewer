package com.markcadag.movieviewer.ui.moviedetails

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.Movie
import com.markcadag.movieviewer.ui.seatmap.SeatMapActivity
import com.markcadag.movieviewer.util.DateUtil
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.item_movie_details.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsMvpView, View.OnClickListener {
    private var movieDetailsPresenter : MovieDetailsPresenter? = null

    /**
     * Lifecycle methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movieDetailsPresenter = MovieDetailsPresenter()
        movieDetailsPresenter?.attachView(this)

        initView()

        showMovieDetails()
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailsPresenter?.detachView()
    }

    /**
     * Mvp methods
     */
    override fun onLoadMovieDetails(movie: Movie) {
        initView(movie)
    }

    override fun onError(errorStringResource: Int) {
        /**
         * Show error dialog when it fails to fetch movies
         * and add retry button
         */
        alert(resources.getString(R.string.failed_to_fetch_movie),
                resources.getString(R.string.something_went_wrong)) {
            yesButton { showMovieDetails() }
            noButton {  }
        }.show()

    }

    /**
     * Impl methods
     */
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.txt_view_seat -> showSeatMap()
        }
    }

    /**
     * Private methods
     */
    private fun showMovieDetails() {
        movieDetailsPresenter?.fetchMovie()
    }

    private fun initView() {
        /**
         * Add titles on the movie view
         */
        item_movie_name.txt_header.text = getString(R.string.name)

        item_movie_genre.txt_header.text = getString(R.string.genre)

        item_movie_advisory.txt_header.text = getString(R.string.advisory)

        item_movie_duration.txt_header.text = getString(R.string.duration)

        item_movie_date.txt_header.text = getString(R.string.date)

        item_movie_syspnosis.txt_header.text = getString(R.string.sypnosis)

        item_movie_cast.txt_header.text = getString(R.string.cast)

        txt_view_seat.setOnClickListener(this)
    }


    private fun initView(movie: Movie) {
        /**
         * Add details on the movie view
         */
        item_movie_name.txt_details.text = movie.canonicalTitle

        item_movie_genre.txt_details.text = movie.genre

        item_movie_advisory.txt_details.text = movie.advisoryRating

        item_movie_duration.txt_details.text = DateUtil.toFormattedDateTime(movie.runtimeMins,
                "mm", "H'hr' mm'mins'")

        item_movie_date.txt_details.text = DateUtil.toFormattedDateTime(movie.releaseDate,
                "yyyy-MM-dd", "MMM d, yyyy" )

        item_movie_syspnosis.txt_details.text = movie.synopsis

        item_movie_cast.txt_details.text = movie.cast?.toCsv()

        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions()
                .placeholder(R.drawable.grey_rect))
                .load(movie.posterLandscape)
                .into(img_mdetails)

        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions()
                        .placeholder(R.drawable.grey_rect))
                .load(movie.poster)
                .into(img_movie_poster)
    }

    private fun showSeatMap() {
        startActivity(Intent(this,SeatMapActivity::class.java))
    }

    /**
     * Extension functions
     *
     */

    /**
     * Converts List<String> value to readable string  value
     */
    fun List<String?>.toCsv() : String {
        return this.toString()
                .replace("[", "", true)
                .replace("]", "", true)
    }
}
