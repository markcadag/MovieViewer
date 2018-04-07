package com.markcadag.movieviewer.ui.seatmap

import com.markcadag.movieviewer.model.Schedule
import com.markcadag.movieviewer.model.SeatMap
import com.markcadag.movieviewer.ui.base.MvpView

/**
 * Created by markcadag on 4/7/18.
 */
interface SeatMapMvpView : MvpView {
    fun onLoadSeatMap(seatMap: SeatMap)
    fun onLoadSchedule(schedule: Schedule)
}