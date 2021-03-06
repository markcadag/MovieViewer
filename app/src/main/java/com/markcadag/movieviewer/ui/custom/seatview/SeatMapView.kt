package com.markcadag.movieviewer.ui.custom.seatview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.SeatMap
import java.util.*

/**
 * Created by markcadag on 4/8/18.
 */
//TODO mind to make it anko?
open class SeatMapView : LinearLayout {
    private var seatMap : SeatMap? = null
    private var onSeatListener : OnSeatListener? = null
    var selectedSeats  = arrayListOf<String>()
    private var maxBooking = 10
    var disableSeatClick = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)



    init {
        this.orientation = LinearLayout.VERTICAL
    }


    fun setSeatMap(seatMap: SeatMap) {
        /**
         * Compute for the weightsum
         */
        seatMap.seatmap?.size?.toFloat()?.let {
            this.weightSum = it
        }
        this.seatMap = seatMap
    }

    fun mapSeatMap() {
        this.addView(LayoutInflater.from(context).inflate(R.layout.row_banner, this, false))
        seatMap?.seatmap?.forEach {
            /**
             * Get row name/title
             */
            var rowName = ""
            it?.first()?.get(0)?.let {
                rowName = it.toString()
            }

            this.addView(createRow(rowName, it))
        }
    }

    /**
     * Private methods
     */
    private fun createRow(name : String,row : List<String?>?) : View {
        val linearContainer = LinearLayout(context)
        linearContainer.gravity = Gravity.CENTER
        linearContainer.orientation = LinearLayout.HORIZONTAL
        val lp = LinearLayout.LayoutParams(MATCH_PARENT, 0)
        lp.weight = 1f
        linearContainer.layoutParams = lp

        /**
         * Guard float value, compute for the weight of the view
         * to perfectly fit on the screen and add 2 more value for the labels
         */
        var weightSum : Float

        row?.size?.toFloat()?.let {
            weightSum = it + 2
            linearContainer.weightSum = weightSum
        }

        /**
         * Add the first row name
         */
        val seatName = SeatNameView(context)
        seatName.setText(name)
        linearContainer.addView(seatName)

        /**
         * Add logic on how the setview should be displayed
         */
        val seats = HashSet(seatMap?.available?.seats)

        row?.forEach {
            it?.let {
               name ->
                var seatStatus = SeatView.SeatStatus.Reserved

                if (selectedSeats.contains(name)) {
                    seatStatus = SeatView.SeatStatus.Selected
                } else if (seats.contains(it)) {
                    seatStatus = SeatView.SeatStatus.Available
                } else if(name.contains("(")) {
                    seatStatus = SeatView.SeatStatus.Isle
                } else if (selectedSeats.contains(it)) {
                    seatStatus = SeatView.SeatStatus.Selected
                }

                val seatView = SeatView(context, name, seatStatus)
                seatView.setOnClickListener {

                    /**
                     * Add view property if seat map is clickable
                     */
                    if(disableSeatClick){
                        onSeatListener?.onClickDisabled()
                        return@setOnClickListener
                    }

                    /**
                     * Disable click event on reserved seats
                     */
                    if (seatView.seatStatus == SeatView.SeatStatus.Reserved) {
                        return@setOnClickListener
                    }

                    /**
                     * Disable click event on isle
                     */
                    if (seatView.seatStatus == SeatView.SeatStatus.Isle) {
                        return@setOnClickListener
                    }

                    if (seatView.seatStatus == SeatView.SeatStatus.Available) {

                        /**
                         * Cancel click listener if booking is maxed out
                         */
                        if (selectedSeats.size >= maxBooking) {
                            onSeatListener?.onBookingMax()
                            return@setOnClickListener
                        }

                        /**
                         * Add seatview to selected seats
                         */
                        seatView.setStatus(SeatView.SeatStatus.Selected)
                        if(!selectedSeats.contains(seatView.name))
                        selectedSeats.add(seatView.name)
                    } else {

                        /**
                         * Remove seat logic
                         */
                        seatView.setStatus(SeatView.SeatStatus.Available)
                        selectedSeats.remove(seatView.name)
                    }
                    onSeatListener?.onClickSeat(it as SeatView)
                }

                linearContainer.addView(seatView)
            }
        }

        /**
         * Add the last row name
         */
        val seatName2 = SeatNameView(context)
        seatName2.setText(name)
        linearContainer.addView(seatName2)

        return linearContainer
    }

    fun maxBooking(maxBooking: Int) {
        this.maxBooking = maxBooking
    }

    fun reset() {
        selectedSeats.clear()
        this.removeAllViews()
        mapSeatMap()
    }

    fun setOnSeatClickListener(onSeatListener: OnSeatListener?){
        this.onSeatListener = onSeatListener
    }

    interface OnSeatListener {
       fun onClickSeat(seatView : SeatView)
       fun onBookingMax()
       fun onClickDisabled()
    }

}