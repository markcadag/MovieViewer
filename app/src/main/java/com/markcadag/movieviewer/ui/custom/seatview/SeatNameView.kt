package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.view.Gravity
import com.markcadag.movieviewer.R



/**
 * Created by markcadag on 4/8/18.
 */
class SeatNameView(context: Context?, rowName: String) : RowItem(context) {

    init {
        text = rowName
        textSize = resources.getDimension(R.dimen._2ssp)
        gravity = Gravity.CENTER
    }
}

