package com.markcadag.movieviewer.ui.seatmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
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

    /**
     * Mvp methods
     */
    override fun onLoadSeatMap(seatMap: SeatMap) {
        initSeatMapView(seatMap)
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

    private fun initSeatMapView(seatMap: SeatMap) {
        seatMap.seatmap?.forEach {
            val linearContainer = LinearLayout(this)
            it?.forEach {
                Log.e(TAG, it)
                val txtView = TextView(this)
                txtView.setText(it)

                linearContainer.addView(txtView)
            }

            ll_seatmap.addView(linearContainer)
        }
    }

    companion object {
        val TAG = SeatMapActivity::class.java.simpleName
    }
}
