package com.markcadag.movieviewer.ui.seatmap

import com.markcadag.movieviewer.model.Cinema
import com.markcadag.movieviewer.model.ScheduleResp
import com.markcadag.movieviewer.model.SeatMap
import com.markcadag.movieviewer.model.Time
import com.markcadag.movieviewer.ui.base.MvpView
import com.markcadag.movieviewer.ui.custom.seatview.SeatView

/**
 * Created by markcadag on 4/7/18.
 */
interface SeatMapMvpView : MvpView {
    fun onLoadSeatMap(seatMap: SeatMap)
    fun onLoadScheduleResp(schedule: ScheduleResp)
    fun onLoadTimeSchedule(times: List<Time?>?)
    fun onErrorParsingSchedule()
    fun onLoadCinemas(cinemas: List<Cinema?>?)
    fun onChangeSelectedSeats(selectedSeatView: ArrayList<SeatView>)
}