package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.view.Gravity
import com.markcadag.movieviewer.R

/**
 * Created by markcadag on 4/8/18.
 */
class SeatView(context: Context?,var name: String,var seatStatus: SeatStatus) : RowItem(context) {

    private var color = R.color.movieViewerGrey

    init {
        gravity = Gravity.CENTER
        setColor()
    }

    fun setStatus(seatStatus: SeatStatus) {
        this.seatStatus = seatStatus
        setColor()
    }

    private fun setColor() {
        if (seatStatus == SeatStatus.Available) {
            color = R.color.movieViewerGrey
        } else if (seatStatus == SeatStatus.Reserved) {
            color = R.color.movieViewerBlue
        } else if (seatStatus == SeatStatus.Space) {
            color = android.R.color.transparent
        } else {
            color = R.color.movieViewerRed
        }

        setBackgroundColor(resources.getColor(this.color))
    }

    enum class SeatStatus {
        Available, Reserved, Selected, Space
    }

}