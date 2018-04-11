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
        } else if (seatStatus == SeatStatus.Isle) {
            color = android.R.color.transparent
        } else if (seatStatus == SeatStatus.Selected) {
            color = R.color.movieViewerRed
        }

        setBackgroundColor(resources.getColor(this.color))
    }

    /**
     * Match width to height to achieve square text
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        setMeasuredDimension(width, width)
    }

    enum class SeatStatus {
        Available, Reserved, Selected, Isle
    }

}