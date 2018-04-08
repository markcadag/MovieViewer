package com.markcadag.movieviewer.ui.seatmap

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.*
import com.markcadag.movieviewer.ui.custom.seatview.SeatMapView
import com.markcadag.movieviewer.ui.custom.seatview.SeatView
import com.markcadag.movieviewer.ui.custom.tagview.RoundedBackgroundSpan
import kotlinx.android.synthetic.main.activity_seat_map.*
import org.jetbrains.anko.alert



class SeatMapActivity : AppCompatActivity(), SeatMapMvpView, AdapterView.OnItemSelectedListener, SeatMapView.OnSeatClickListener {
    lateinit var seatMaprepsenter : SeatMapPresenter
    lateinit var scheduleAdapterDate: ScheduleAdapter
    lateinit var scheduleAdapterCinema: ScheduleAdapter
    lateinit var scheduleAdapterTime: ScheduleAdapter
    private var schedule : ScheduleResp? = null
    private var alertDIalog: Dialog? = null

    /**
     * Lifecycle methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_map)

        seatMaprepsenter = SeatMapPresenter()
        seatMaprepsenter.attachView(this)

        alertDIalog = alert(resources.getString(R.string.failed_to_fetch_schedule),
                resources.getString(R.string.something_went_wrong))
                .build()

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
        schedule?.let {
            when(parent?.id) {
                R.id.spinner_date -> {
                    seatMaprepsenter.filterCinema(scheduleAdapterDate.getItem(position) as Date, it)
                }

                R.id.spinner_cinema -> {
                    seatMaprepsenter.filterTime(scheduleAdapterCinema.getItem(position) as Cinema, it)
                }
            }
        }
    }

    override fun onClickSeat(seatView: SeatView) {
        Log.e(TAG, seatView.name)
//        if(seatView.seatStatus == SeatView.SeatStatus.Available) {
//            seatView.setStatus(SeatView.SeatStatus.Selected)
//        } else {
//            seatView.setStatus(SeatView.SeatStatus.Available)
//        }

        updateSelectedSeatsView()
    }

    override fun onBookingMax() {
        alert { resources.getString(R.string.booking_maxed_out) }.show()
    }

    /**
     * Mvp methods
     */
    override fun onLoadSeatMap(seatMap: SeatMap) {
        seatmap_view.setSeatMap(seatMap)
        seatmap_view.mapSeatMap()
        seatmap_view.maxBooking(6)
        seatmap_view.setONSeatClickListener(this)
        seatMaprepsenter.observeSelectedSeats()
    }

    override fun onLoadScheduleResp(schedule: ScheduleResp) {
        this.schedule = schedule
        initAdapters()
    }

    override fun onLoadTimeSchedule(times: List<Time?>?) {
        scheduleAdapterTime.clear()
        scheduleAdapterTime.addAll(times)
    }

    override fun onLoadCinemas(cinemas: List<Cinema?>?) {
        scheduleAdapterCinema.clear()
        scheduleAdapterCinema.addAll(cinemas)
    }

    override fun onErrorParsingSchedule() {
        scheduleAdapterCinema.clear()
        scheduleAdapterCinema.notifyDataSetChanged()

        scheduleAdapterTime.clear()
        scheduleAdapterTime.notifyDataSetChanged()
    }

    override fun onChangeSelectedSeats(selectedSeatView: ArrayList<SeatView>) {
        Log.e(TAG, selectedSeatView.toString())
    }

    override fun onError(errorStringResource: Int) {
        alertDIalog?.let {
            if (!it.isShowing) {
                alertDIalog?.show()
            }
        }
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

    private fun updateSelectedSeatsView() {
        val stringBuilder = SpannableStringBuilder()

        var between = ""
        seatmap_view.selectedSeats.forEach{
            stringBuilder.append(between)
            if (between.length == 0) between = "  "
            val thisTag = "  ${it.name}  "
            stringBuilder.append("  ${it.name}  ")
            stringBuilder.setSpan(RoundedBackgroundSpan(this), stringBuilder.length - thisTag.length,
                    stringBuilder.length - thisTag.length + thisTag.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        txt_selected_seats.text = stringBuilder
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

    companion object {
        val TAG = SeatMapActivity::class.java.simpleName
    }
}
