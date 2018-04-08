package com.markcadag.movieviewer.ui.seatmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.Schedule
import com.markcadag.movieviewer.model.SeatMap
import kotlinx.android.synthetic.main.activity_seat_map.*

class SeatMapActivity : AppCompatActivity(), SeatMapMvpView {
    lateinit var seatMaprepsenter : SeatMapPresenter

    /**
     * Lifecycle methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_map)

        seatMaprepsenter = SeatMapPresenter()
        seatMaprepsenter.attachView(this)

        fetchSeatMap()
        fetchSchedule()
    }

    override fun onDestroy() {
        super.onDestroy()
        seatMaprepsenter.detachView()
    }

    /**
     * Mvp methods
     */
    override fun onLoadSeatMap(seatMap: SeatMap) {
        seatmap_view.setSeatMap(seatMap)
        seatmap_view.mapSeatMap()
    }

    override fun onLoadSchedule(schedule: Schedule) {

    }

    override fun onError(errorStringResource: Int) {

    }

    /**
     * Private methods
     */
    private fun fetchSeatMap() {
        seatMaprepsenter.fetchSeatMap()
    }

    private fun fetchSchedule() {
        seatMaprepsenter.fetchSchedule()
    }

    private fun onItemSelected() {
//        ll_selected_seats.addView()
    }

    companion object {
        val TAG = SeatMapActivity::class.java.simpleName
    }
}
