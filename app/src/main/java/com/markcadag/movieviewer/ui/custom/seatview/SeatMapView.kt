package com.markcadag.movieviewer.ui.custom.seatview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.markcadag.movieviewer.model.SeatMap
import java.util.*

/**
 * Created by markcadag on 4/8/18.
 */
open class SeatMapView : LinearLayout {
    private var seatMap : SeatMap? = null

    //TODO make use of anko

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        this.orientation = LinearLayout.VERTICAL
        this.addView(RowBanner(context))
    }

    fun setSeatMap(seatMap: SeatMap) {
        this.seatMap = seatMap
    }

    fun mapSeatMap() {
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
        linearContainer.addView(SeatNameView(context, name))

        /**
         * Add logic on how the setview should be displayed
         */
        val seats = HashSet(seatMap?.available?.seats)

        row?.forEach {
            if(it!!.contains("(")) {
                /**
                 * Add isle view is text contains "(" string value
                 */
                linearContainer.addView(IsleView(context))
            } else {
                val seatView = SeatView(context)
                if (seats.contains(it)) {
                    seatView.name = it
                    seatView.seatStatus = SeatView.SeatStatus.Available
                    linearContainer.addView(seatView)
                } else {
                    seatView.name = it
                    seatView.seatStatus = SeatView.SeatStatus.Reserved
                    linearContainer.addView(seatView)
                }
            }
        }

        /**
         * Add the last row name
         */
        linearContainer.addView(SeatNameView(context, name))

        return linearContainer
    }

    companion object {
        val TAG = SeatMapView::class.java.simpleName
    }
}