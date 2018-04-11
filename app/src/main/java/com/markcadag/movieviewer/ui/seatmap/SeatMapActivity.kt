package com.markcadag.movieviewer.ui.seatmap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.*
import com.markcadag.movieviewer.ui.base.BaseActivity
import com.markcadag.movieviewer.ui.custom.seatview.SeatMapView
import com.markcadag.movieviewer.ui.custom.seatview.SeatView
import com.markcadag.movieviewer.util.TextUtil
import kotlinx.android.synthetic.main.activity_seat_map.*
import kotlinx.android.synthetic.main.item_frame_setview.view.*
import kotlinx.android.synthetic.main.item_ll_schedule.view.*
import kotlinx.android.synthetic.main.item_ll_selected_seats.view.*
import kotlinx.android.synthetic.main.item_ll_total_price.view.*
import org.jetbrains.anko.toast


class SeatMapActivity : BaseActivity(), SeatMapMvpView, AdapterView.OnItemSelectedListener, SeatMapView.OnSeatClickListener {
    lateinit var seatMaprepsenter : SeatMapPresenter
    lateinit var scheduleAdapterDate: ScheduleAdapter
    lateinit var scheduleAdapterCinema: ScheduleAdapter
    lateinit var scheduleAdapterTime: ScheduleAdapter
    private var schedule : ScheduleResp? = null
    private val MAX_BOOKING = 10
    private var selectedSeats = arrayListOf<String>()

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
     * Restore activity state when switching to landscape mode
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("selected_seats", item_frame_seatview.seatmap_view.selectedSeats);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedSeats = savedInstanceState?.getStringArrayList("selected_seats") as ArrayList<String>
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

                R.id.spinner_time -> {
                    updateViews()
                }
            }
        }
    }

    override fun onClickSeat(seatView: SeatView) {
       updateViews()
    }

    override fun onBookingMax() {
        toast(resources.getString(R.string.booking_maxed_out))
    }

    /**
     * Mvp methods
     */
    override fun onTaskStarted() {
        showProgressDialog()
    }

    override fun onComplete() {
        dismissProgressDialog()
    }

    override fun onLoadSeatMap(seatMap: SeatMap) {
        item_frame_seatview.seatmap_view.setSeatMap(seatMap)
        item_frame_seatview.seatmap_view.selectedSeats = selectedSeats
        item_frame_seatview.seatmap_view.mapSeatMap()
        item_frame_seatview.seatmap_view.maxBooking(MAX_BOOKING)
        item_frame_seatview.seatmap_view.setOnSeatClickListener(this)
    }

    override fun onLoadScheduleResp(schedule: ScheduleResp) {
        this.schedule = schedule
        initAdapters()
    }

    override fun onLoadTimeSchedule(times: List<Time?>?) {
        scheduleAdapterTime.clear()
        scheduleAdapterTime.addAll(times)
        item_ll_schedule.spinner_time.onItemSelectedListener = this
    }

    override fun onLoadCinemas(cinemas: List<Cinema?>?) {
        scheduleAdapterCinema.clear()
        scheduleAdapterCinema.addAll(cinemas)
    }

    override fun onFailedToFetchTime() {
        scheduleAdapterTime.clear()
        scheduleAdapterTime.notifyDataSetChanged()
    }

    override fun onFailedToFetchCinema() {
        scheduleAdapterCinema.clear()
        scheduleAdapterCinema.notifyDataSetChanged()

        onFailedToFetchTime()
    }

    override fun onChangeSelectedSeats(selectedSeatView: ArrayList<SeatView>) {
        Log.e(TAG, selectedSeatView.toString())
    }

    override fun onError(errorStringResource: Int) {
        showErrorDialog()
        dismissProgressDialog()
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
        val stringList = arrayListOf<String>()
        item_frame_seatview.seatmap_view.selectedSeats.forEach {
            stringList.add(it)
        }
        item_ll_selected_seats.txt_selected_seats.text = TextUtil.toTagView(this,stringList)
    }

    private fun updateCost() {
        if(item_ll_schedule.spinner_time.selectedItemPosition != -1) {
            val time = scheduleAdapterTime.getItem(item_ll_schedule.spinner_time.selectedItemPosition) as? Time
            time?.price?.toFloat()?.let {
                val total = (it * item_frame_seatview.seatmap_view.selectedSeats.size).toDouble()
                item_ll_total_price.txt_total_cost.text = TextUtil.toFormattedPrice(total)
            }
        }
    }

    private fun updateViews() {
        updateSelectedSeatsView()
        updateCost()
    }

    private fun initAdapters() {
        scheduleAdapterDate = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        scheduleAdapterDate.addAll(schedule?.dates)
        item_ll_schedule.spinner_date.adapter = scheduleAdapterDate
        item_ll_schedule.spinner_date.onItemSelectedListener = this

        scheduleAdapterCinema = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        item_ll_schedule.spinner_cinema.adapter = scheduleAdapterCinema
        item_ll_schedule.spinner_cinema.onItemSelectedListener = this

        scheduleAdapterTime = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        item_ll_schedule.spinner_time.adapter = scheduleAdapterTime
    }

    companion object {
        val TAG = SeatMapActivity::class.java.simpleName
    }
}
