package com.markcadag.movieviewer.ui.seatmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.*
import kotlinx.android.synthetic.main.activity_seat_map.*

class SeatMapActivity : AppCompatActivity(), SeatMapMvpView, AdapterView.OnItemSelectedListener {
    lateinit var seatMaprepsenter : SeatMapPresenter
    lateinit var scheduleAdapterDate: ScheduleAdapter
    lateinit var scheduleAdapterCinema: ScheduleAdapter
    lateinit var scheduleAdapterTime: ScheduleAdapter
    private var schedule : ScheduleResp? = null

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
     * Impl methods
     */
    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent?.id) {
            R.id.spinner_date -> {
                filterCinema(scheduleAdapterDate.getItem(position) as Date)
            }

            R.id.spinner_cinema -> {
                filterTime(scheduleAdapterCinema.getItem(position) as Cinema)
            }
        }
    }

    /**
     * Mvp methods
     */
    override fun onLoadSeatMap(seatMap: SeatMap) {
        seatmap_view.setSeatMap(seatMap)
        seatmap_view.mapSeatMap()
    }

    override fun onLoadScheduleResp(schedule: ScheduleResp) {
        this.schedule = schedule
        initAdapters()
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

    private fun initAdapters() {
        scheduleAdapterDate = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        scheduleAdapterDate.addAll(schedule?.dates)
        spinner_date.adapter = scheduleAdapterDate
        spinner_date.onItemSelectedListener = this

        scheduleAdapterCinema = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        spinner_cinema.adapter = scheduleAdapterCinema
        spinner_cinema.onItemSelectedListener = this

        scheduleAdapterTime = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        spinner_time.adapter = scheduleAdapterTime
    }

    private fun filterCinema(date : Date) {
        date.id?.let {
            dateSelected ->
            try {
                val cinemaRoot: CinemaRoot? = schedule?.cinemasRoot?.single {
                    it?.parent == dateSelected
                }

                scheduleAdapterCinema.clear()
                scheduleAdapterCinema.addAll(cinemaRoot?.cinemas)
            } catch (e: Exception) {
                scheduleAdapterCinema.clear()
                scheduleAdapterCinema.notifyDataSetChanged()
            }
        }
    }

    private fun filterTime(cinema : Cinema) {
        cinema.id?.let {
            cinemaId ->
            try {
                val timeRoot: TimeRoot? = schedule?.times?.single {
                    it?.parent == cinemaId
                }

                scheduleAdapterTime.clear()
                scheduleAdapterTime.addAll(timeRoot?.times)
            } catch (e: Exception) {
                scheduleAdapterTime.clear()
                scheduleAdapterTime.notifyDataSetChanged()
            }
        }
    }

    companion object {
        val TAG = SeatMapActivity::class.java.simpleName
    }
}
