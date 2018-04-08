package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.view.Gravity

/**
 * Created by markcadag on 4/8/18.
 */
class SeatNameView(context: Context?, rowName: String) : RowItem(context) {
    init {
        text = rowName
        gravity = Gravity.CENTER
    }
}