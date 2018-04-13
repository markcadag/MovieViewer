package com.markcadag.movieviewer.ui.seatmap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.*
import com.markcadag.movieviewer.model.Date
import com.markcadag.movieviewer.ui.base.BaseActivity
import com.markcadag.movieviewer.ui.custom.seatview.SeatMapView
import com.markcadag.movieviewer.ui.custom.seatview.SeatView
import com.markcadag.movieviewer.ui.custom.seatview.ZoomView
import com.markcadag.movieviewer.ui.custom.tagview.MainChipViewAdapter
import com.markcadag.movieviewer.ui.custom.tagview.SeatChip
import com.markcadag.movieviewer.util.TextUtil
import com.plumillonforge.android.chipview.Chip
import kotlinx.android.synthetic.main.activity_seat_map.*
import kotlinx.android.synthetic.main.item_ll_schedule.view.*
import kotlinx.android.synthetic.main.item_ll_selected_seats.view.*
import kotlinx.android.synthetic.main.item_ll_total_price.view.*
import org.jetbrains.anko.toast
import java.util.*


class SeatMapActivity : BaseActivity(), SeatMapMvpView, AdapterView.OnItemSelectedListener, SeatMapView.OnSeatListener {
    lateinit var seatMaprepsenter : SeatMapPresenter
    lateinit var scheduleAdapterDate: ScheduleAdapter
    lateinit var scheduleAdapterCinema: ScheduleAdapter
    lateinit var scheduleAdapterTime: ScheduleAdapter
    private var schedule : ScheduleResp? = null
    private val MAX_BOOKING = 10
    private var selectedSeats = arrayListOf<String>()
    private var zoomView: ZoomView? = null
    private var seatMapView: SeatMapView? = null
    private var cinemaPrice = 0.00
    var check = 0

    private val mainChipViewAdapter : MainChipViewAdapter by lazy {
        MainChipViewAdapter(this)
    }

    /**
     * Lifecycle methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_map)

        seatMaprepsenter = SeatMapPresenter()
        seatMaprepsenter.attachView(this)

        zoomView = ZoomView(this)
        seatMapView = SeatMapView(this)
        seatMapView?.setOnSeatClickListener(this)

        zoomView?.addView(seatMapView)

        item_frame_seatview.addView(zoomView)

        fetchSeatMap()
        fetchSchedule()
    }

    /**
     * Restore activity state when switching to landscape mode
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("selected_seats", seatMapView?.selectedSeats);
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
                    resetView()
                }

                R.id.spinner_cinema -> {
                    seatMaprepsenter.filterTime(scheduleAdapterCinema.getItem(position) as Cinema, it)
                    resetView()
                }

                R.id.spinner_time -> {
                    val time = scheduleAdapterTime.getItem(position) as? Time
                    time?.price?.let {
                        resetView()
                        cinemaPrice = it.toDouble()
                        seatMapView?.disableSeatClick = false
                    }
                }
                else -> { }
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
        seatMapView?.setSeatMap(seatMap)
        seatMapView?.selectedSeats = selectedSeats
        seatMapView?.mapSeatMap()
        seatMapView?.maxBooking(MAX_BOOKING)
    }

    override fun onLoadScheduleResp(schedule: ScheduleResp) {
        this.schedule = schedule
        initAdapters()
    }

    override fun onLoadTimeSchedule(times: List<Time?>?) {
        scheduleAdapterTime = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        scheduleAdapterTime.addAll(times)
        item_ll_schedule.spinner_time.adapter = scheduleAdapterTime
        item_ll_schedule.spinner_time.onItemSelectedListener = this
    }

    override fun onLoadCinemas(cinemas: List<Cinema?>?) {
        scheduleAdapterCinema = ScheduleAdapter(this,
                R.layout.custom_dropdown_item, R.id.txt_title)
        scheduleAdapterCinema.addAll(cinemas)
        item_ll_schedule.spinner_cinema.adapter = scheduleAdapterCinema
        item_ll_schedule.spinner_cinema.onItemSelectedListener = this
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
        val chip = arrayListOf<Chip>()
        seatMapView?.selectedSeats?.forEach {
            chip.add(SeatChip(it))
        }

        item_ll_selected_seats.chipview.adapter = mainChipViewAdapter
        item_ll_selected_seats.chipview.chipList = chip
    }

    private fun updateCost() {
        val total = (cinemaPrice * seatMapView!!.selectedSeats.size)
        item_ll_total_price.txt_total_cost.text = TextUtil.toFormattedPrice(total)
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
    }

    /**
     * Reset views, and data state
     */
    private fun resetView() {
        cinemaPrice = 0.00
        seatMapView?.disableSeatClick = true
        unZoomView()
        item_ll_total_price.txt_total_cost.text = getString(R.string.php_0_00)
        seatMapView?.reset()
        updateViews()
    }

    private fun unZoomView() {
        zoomView?.smoothZoomTo(1.0f, 0f, 0f)
    }

    companion object {
        val TAG = SeatMapActivity::class.java.simpleName
    }
}
